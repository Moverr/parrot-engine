package services

import entities.requests.kiosks.KioskRequest
import entities.responses.accounts.AccountResponse
import entities.responses.kiosks.KioskResponse
import entities.responses.stations.StationResponse
import play.api.db.DB._
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest
import utils.HelperUtilities

import scala.collection.mutable.ListBuffer

//////
import play.api.Play.current
///////


class KioskService {
  val tableName = " \"default\".kiosks"

  var conn = getConnection()

  //todo: create
  @throws
  def create(owner: Integer, kiosk: KioskRequest): Unit = {
    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")
    //todo: check to see that  station exists

    val station: StationResponse = StationsService.getById(owner, kiosk.station_id)
    if (station == null) throw new RuntimeException("Station Does not exist")

    val account: AccountResponse = AccountService.get(owner);
    if (checkIfKioskExists(account.id, kiosk.reference_id) == true) throw new RuntimeException("Account already created for user")


    var query = "INSERT INTO " + tableName + " (reference,details,device_token,station_id,author_id)  values ('" + HelperUtilities.randomStringGenerator(10) + "','" + kiosk.details + "','" + kiosk.token + "','" + kiosk.station_id + "','" + owner + "' ) ";
    conn = getConnection()
    val stmt = conn.createStatement
    val result = stmt executeUpdate (query)


  }

  //todo: Get All
  def getAllByAccount(accountId: Integer, offset: Long = 0, limit: Long = 10): Seq[KioskResponse] = {
    //todo: verify that owner is not null
    if (accountId == null) BadRequest(Json.obj(s"status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(accountId);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))


    var query = "SELECT A.* FROM   " + tableName + "  A INNER JOIN " + StationsService.tableName + " B  WHERE B.account_id = " + account.id + "  offset " + offset + " limit " + limit + "  ";
    conn = getConnection()
    val stmt = conn createStatement
    val result = stmt.executeQuery(query)

    val kioskResponses = new ListBuffer[KioskResponse]()

    while (result next()) {
      val kioskResponse: KioskResponse = new KioskResponse(result.getInt("id"), result.getString("reference"), result.getString("details"), result.getString("device_token"), result.getDate("created_on"))
      kioskResponses += kioskResponse
    }
    kioskResponses.toSeq


  }

  //todo: get Station by Id
  @throws
  def getById(owner: Integer, kioskId: Long): StationResponse = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    var query = "SELECT * FROM   " + tableName + " WHERE id = " + kioskId + "   AND    ";
    conn = getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) {
      val id = result.getInt("id")
      val name = result.getString("name")
      val code = result.getString("code")
      val response = new StationResponse(id, name, code)
      response
    } else
      throw new RuntimeException("Record does not exist in the database")
  }


  //todo: Archive Station
  def Archive(owner: Integer, stationId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))

    val stationResponse: StationResponse = getById(owner, stationId)

    if (stationResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ARCHIVED' where id='" + stationId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
    }


  }


  //todo: Activate
  def Activate(owner: Integer, stationId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))

    val stationResponse: StationResponse = getById(owner, stationId)

    if (stationResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ACTIVE' where id='" + stationId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
    }

  }


  def checkIfKioskExists(accountId: Integer, reference_id: String): Boolean = {
    var query = "SELECT * FROM   " + tableName + " WHERE account_id = " + accountId + "  AND name LIKE '" + reference_id + "' ";
    conn = getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) {
      true
    } else {
      false
    }
  }

}

object KioskService extends KioskService
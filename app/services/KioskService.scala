package services

import entities.requests.stations.StationRequest
import entities.responses.accounts.AccountResponse
import entities.responses.stations.StationResponse
import play.api.db.DB
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest

import scala.collection.mutable.ListBuffer

class KioskService {
  implicit val tableName = " \"default\".stations"

  implicit var conn = DB.getConnection()

  //todo: create
  @throws
  def create(owner: Integer, station: StationRequest): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))
    //todo: get Account Id by owner
    val account: AccountResponse = AccountService.get(owner);


    if (checkIfStationExists(account.id, station.name) == true) {
      throw new RuntimeException("Account already created for user")
    } else {
      var query = "INSERT INTO " + tableName + " (account_id,name,code)  values ('" + account.id + "','" + station.name + "','" + station.code + "') ";
      conn = DB getConnection()
      val stmt = conn.createStatement
      val result = stmt executeUpdate (query)
    }

  }

  //todo: Get All
  def getAll(owner: Integer, offset: Long = 0, limit: Long = 10): Seq[StationResponse] = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj(s"status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))


    var query = "SELECT * FROM   " + tableName + " WHERE account_id = " + account.id + "  offset " + offset + " limit " + limit + "  ";
    conn = DB getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)

    val stationResponses = new ListBuffer[StationResponse]()

    while (result next()) {
      val stationResponse: StationResponse = new StationResponse(result.getInt("id"), result.getString("name"), result.getString("code"))
      stationResponses += stationResponse
    }
    stationResponses.toSeq


  }

  //todo: get Station by Id
  @throws
  def getById(owner: Integer, stationId: Long): StationResponse = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    var query = "SELECT * FROM   " + tableName + " WHERE id = " + stationId + "     ";
    conn = DB getConnection()
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
      conn = DB getConnection()
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
      conn = DB getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
    }

  }


  def checkIfStationExists(accountId: Integer, stationName: String): Boolean = {
    var query = "SELECT * FROM   " + tableName + " WHERE account_id = " + accountId + "  AND name LIKE '" + stationName + "' ";
    conn = DB getConnection()
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
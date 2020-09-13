package services

import entities.requests.kiosks.KioskRequest
import entities.responses.accounts.AccountResponse
import entities.responses.kiosks.KioskResponse
import entities.responses.stations.StationResponse
import javax.inject.Inject
import play.api.db.DB._
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest
import utils.HelperUtilities

import scala.collection.mutable.ListBuffer

//////
import play.api.Play.current
///////


class KioskService @Inject()(stationsService: StationsService) extends TKioskService {
  override val tableName = " \"default\".kiosks"

  override var conn = getConnection()

  @throws
  private def validate(kiosk: KioskRequest): Unit = {
    if (kiosk.station_id == 0) throw new RuntimeException("Station id is mandatory")
    if (kiosk.token.length == 0) throw new RuntimeException("Token  is mandatory")
    if (kiosk.details.length == 0) throw new RuntimeException("Kiosk Details is mandatory  ")

  }

  //todo: create
  @throws
  override def create(owner: Integer, kiosk: KioskRequest): Unit = {


    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")
    //todo: check to see that  station exists

    val station: StationResponse = stationsService.getById(owner, kiosk.station_id)
    if (station == null) throw new RuntimeException("Station Does not exist")

    validate(kiosk)

    val account: AccountResponse = AccountService.get(owner);
    val reference = HelperUtilities.randStr(10);
    val query = "INSERT INTO " + tableName + " (reference,details,device_token,station_id,author_id)  values ('" + reference + "','" + kiosk.details + "','" + kiosk.token + "','" + kiosk.station_id + "','" + owner + "' ) ";
    conn = getConnection()
    val stmt = conn.createStatement
    val result = stmt executeUpdate (query)
    conn.close()

  }

  //todo: Get All By Account
  override def getAll(owner: Integer, offset: Long = 0, limit: Long = 10): Seq[KioskResponse] = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj(s"status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))

    val query = "SELECT A.* FROM   " + tableName + "  A INNER JOIN " + stationsService.tableName + " B ON A.station_id = B.id  WHERE B.account_id = " + account.id + "  offset " + offset + " limit " + limit + "  ";
    conn = getConnection()
    val stmt = conn createStatement
    val result = stmt.executeQuery(query)

    val kioskResponses = new ListBuffer[KioskResponse]()

    while (result next()) {
      val kioskResponse: KioskResponse = new KioskResponse(result.getInt("id"), result.getString("reference"), result.getString("details"), result.getString("device_token"), result.getDate("created_on"))
      kioskResponses += kioskResponse
    }
    conn.close()
    kioskResponses.toSeq

  }


  //todo: Get All By Account
  override def getAllByStation(owner: Integer, stationId: Long, offset: Long = 0, limit: Long = 10): Seq[KioskResponse] = {
    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")


    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Account does not exist")

    val station: StationResponse = stationsService.getById(owner, stationId.longValue())
    if (station == null) throw new RuntimeException("Station does not exist")

    val query = "SELECT A.* FROM   " + tableName + "  A INNER JOIN " + stationsService.tableName + " B  WHERE B.id = " + station.id + "  offset " + offset + " limit " + limit + "  ";
    conn = getConnection()
    val stmt = conn createStatement
    val result = stmt.executeQuery(query)

    val kioskResponses = new ListBuffer[KioskResponse]()

    while (result next()) {
      val kioskResponse: KioskResponse = new KioskResponse(result.getInt("id"), result.getString("reference"), result.getString("details"), result.getString("device_token"), result.getDate("created_on"))
      kioskResponses += kioskResponse
    }
    conn.close()
    kioskResponses.toSeq

  }


  //todo: get Station by Id
  @throws
  override def getById(owner: Integer, kioskId: Long): KioskResponse = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    var query = "SELECT * FROM   " + tableName + " WHERE id = " + kioskId + "   AND    ";
    conn = getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) {
      val response: KioskResponse = new KioskResponse(result.getInt("id"), result.getString("reference"), result.getString("details"), result.getString("device_token"), result.getDate("created_on"))
      response
    } else
      throw new RuntimeException("Record does not exist in the database")
  }


  //todo: Archive Station
  override def Archive(owner: Integer, stationId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))

    val kioskResponse: KioskResponse = getById(owner, stationId)

    if (kioskResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ARCHIVED' where id='" + stationId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
      conn.close()
    }


  }


  //todo: Activate
  override def Activate(owner: Integer, kioskId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))

    val kioskResponse: KioskResponse = getById(owner, kioskId)

    if (kioskResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ACTIVE' where id='" + kioskId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
      conn.close()
    }

  }


  //todo: check if kiosk exists
  override def checkIfKioskExists(accountId: Integer, reference_id: String): Boolean = {
    var query = "SELECT * FROM   " + tableName + " A INNER JOIN " + stationsService.tableName + " B WHERE B.account_id = " + accountId + "  AND reference LIKE '" + reference_id + "' ";
    conn = getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) {
      conn.close()
      true
    } else {
      conn.close()
      false
    }

  }

}

object KioskService{
  def apply(stsationService:StationsService):KioskService = new KioskService(stsationService)
}


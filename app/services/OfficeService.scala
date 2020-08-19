package services

import java.sql.ResultSet

import entities.requests.offices.OfficeRequest
import entities.responses.accounts.AccountResponse
import entities.responses.offices.OfficeResponse
import play.api.db.DB.getConnection
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest

import scala.collection.mutable.ListBuffer

//////
import play.api.Play.current
///////


class OfficeService {
  val tableName = " \"default\".offices"
  var conn = getConnection()

  @throws
  private def validate(office: OfficeRequest): Unit = {
    if (office.name == null) throw new RuntimeException("Office Name id is mandatory")
    if (office.code == null) throw new RuntimeException("Office Code  is mandatory")
  }

  //todo: create
  @throws
  def create(owner: Integer, office: OfficeRequest): Unit = {

    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")
    //todo: check to see that  station exists

    validate(office)

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Invalid Authentication")

    val query = "INSERT INTO " + tableName + " (name,parent_office,account_id,author_id)  values ( '" + office.name + "','" + office.parent_office + "','" + account.id + "','" + owner + "' ) ";
    conn = getConnection()
    val stmt = conn.createStatement
    val result = stmt executeUpdate (query)
    conn.close()

  }

  //todo: Get All By Account
  def getAll(owner: Integer, offset: Long = 0, limit: Long = 10): Seq[OfficeResponse] = {
    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Account does not exist")

    val query = "SELECT A.*,C.name as parent_office_name  FROM   " + tableName + "   A LEFT OUTER JOIN "+tableName+"  C ON C.id = A.parent_office INNER JOIN " + AccountService.tableName + " B ON A.account_id = B.id  WHERE B.id = " + account.id + "  offset " + offset + " limit " + limit + "  ";
    conn = getConnection()
    val stmt = conn createStatement
    val result = stmt.executeQuery(query)

    val officeResponses = new ListBuffer[OfficeResponse]()

    while (result next()) {
      val officeResponse: OfficeResponse = populateResponse(result)
      officeResponses += officeResponse
    }
    conn.close()
    officeResponses.toSeq
  }


  private def populateResponse(result: ResultSet) = {
    new OfficeResponse(result.getInt("id"), result.getString("name"), result.getString("parent_office_name"), result.getDate("created_on"))
  }

  //todo: get Station by Id
  @throws
  def getById(owner: Integer, officeId: Long): OfficeResponse = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val query = "SELECT A.* FROM   " + tableName + "  A INNER JOIN " + AccountService.tableName + " B ON A.account_id = B.id  WHERE  A.id ='" + officeId + "' ";

    conn = getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) {
      val response: OfficeResponse = populateResponse(result)
      response
    } else
      throw new RuntimeException("Record does not exist in the database")
  }


  //todo: Archive Station
  def Archive(owner: Integer, officeId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))


    val officeResponse: OfficeResponse = getById(owner, officeId)

    if (officeResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ARCHIVED' where id='" + officeId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
      conn.close()
    }


  }


  //todo: Activate
  def Activate(owner: Integer, officeId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))

    val officeResponse: OfficeResponse = getById(owner, officeId)

    if (officeResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ARCHIVED' where id='" + officeId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
      conn.close()
    }

  }


  //todo: check if kiosk exists
  def checkIfKioskExists(accountId: Integer, reference_id: String): Boolean = {
    var query = "SELECT * FROM   " + tableName + " A INNER JOIN " + StationsService.tableName + " B WHERE B.account_id = " + accountId + "  AND reference LIKE '" + reference_id + "' ";
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
object OfficeService extends OfficeService
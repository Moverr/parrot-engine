package services

import java.sql.ResultSet

import entities.requests.employee.{EmployeeAsignRequest, EmployeeRequest}
import entities.responses.accounts.AccountResponse
import entities.responses.employee.EmployeeResponse
import javax.inject.Inject
import play.api.db.DB.getConnection
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest

import scala.collection.mutable.ListBuffer

//////
import play.api.Play.current
///////


class EmployeeService @Inject()(stationsService: StationsService) {
  val tableName = " \"default\".employees"
  val assignTableName = " \"default\".employee_station"
  var conn = getConnection()

  @throws
  private def validate(employee: EmployeeRequest): Unit = {
    if (employee.names == null) throw new RuntimeException("Office Name id is mandatory")
    if (employee.gender == null) throw new RuntimeException("Office Gender  is mandatory")
  }

  @throws
  private def validate(office: EmployeeAsignRequest): Unit = {

    if (office.office_id == 0) throw new RuntimeException("Office ID id is mandatory")
    if (office.station_id == 0) throw new RuntimeException("station ID  is mandatory")
    if (office.employee_id == 0) throw new RuntimeException("employee  ID  is mandatory")
  }

  //todo: create
  @throws
  def create(owner: Integer, employee: EmployeeRequest): Unit = {

    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")
    //todo: check to see that  station exists

    validate(employee)

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Invalid Authentication")

    val query = "INSERT INTO " + tableName + " (names,gender,account_id,author_id)  values ( '" + employee.names + "','" + employee.gender + "','" + account.id + "','" + owner + "' ) ";
    conn = getConnection()
    val stmt = conn.createStatement
    val result = stmt executeUpdate (query)
    conn.close()

  }


  @throws
  def assign(owner: Integer, employeeAssign: EmployeeAsignRequest): Unit = {

    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")
    //todo: check to see that  station exists

    validate(employeeAssign)

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Invalid Authentication")

    val query = "INSERT INTO " + assignTableName + " (employee_id,office_id,station_id)  values ( '" + employeeAssign.employee_id + "','" + employeeAssign.office_id + "','" + employeeAssign.station_id + "') ";
    conn = getConnection()
    val stmt = conn.createStatement
    val result = stmt executeUpdate (query)
    conn.close()
  }


  private def populateResponse(result: ResultSet) = new EmployeeResponse(result.getInt("id"), result.getString("names"), result.getString("gender"), result.getDate("created_on"))


  //todo: Get All By Account
  def getAll(owner: Integer, offset: Long = 0, limit: Long = 10): Seq[EmployeeResponse] = {
    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Account does not exist")

    val query = "SELECT A.*,C.name as parent_office_name  FROM   " + tableName + "   A LEFT OUTER JOIN " + tableName + "  C ON C.id = A.parent_office INNER JOIN " + AccountService.tableName + " B ON A.account_id = B.id  WHERE B.id = " + account.id + "  offset " + offset + " limit " + limit + "  ";
    conn = getConnection()
    val stmt = conn createStatement
    val result = stmt.executeQuery(query)

    val officeResponses = new ListBuffer[EmployeeResponse]()

    while (result next()) {
      officeResponses += populateResponse(result)
    }
    conn.close()
    officeResponses.toSeq
  }


  //todo: get Station by Id
  @throws
  def getById(owner: Integer, officeId: Long): EmployeeResponse = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val query = "SELECT A.* FROM   " + tableName + "  A INNER JOIN " + AccountService.tableName + " B ON A.account_id = B.id  WHERE  A.id ='" + officeId + "' ";

    conn = getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) {
      populateResponse(result)
    } else
      throw new RuntimeException("Record does not exist in the database")
  }


  //todo: Archive Station
  def Archive(owner: Integer, officeId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))


    val employeeResponse: EmployeeResponse = getById(owner, officeId)

    if (employeeResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
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

    val officeResponse: EmployeeResponse = getById(owner, officeId)

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
  def checkIfEmployeeExists(accountId: Integer): Boolean = {
    var query = "SELECT * FROM   " + tableName + " A INNER JOIN " + stationsService.tableName + " B WHERE B.account_id = " + accountId + "  ";
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

object EmployeeService{
  def apply(stationService: StationsService):EmployeeService = new EmployeeService(stationService)
}




//object EmployeeService extends EmployeeService
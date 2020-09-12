package services

import java.sql.ResultSet

import entities.requests.departments.DepartmentRequest
import entities.responses.accounts.AccountResponse
import entities.responses.departments.DepartmentResponse
import javax.inject.Inject
import play.api.db.DB.getConnection
import play.api.libs.json.Json
import play.api.mvc.Results.BadRequest

import scala.collection.mutable.ListBuffer

//////
import play.api.Play.current
///////


class DepartmentService  @Inject()(stationsService: StationsService) {
  val tableName = " \"default\".departments"
  var conn = getConnection()

  //def _stationService :StationsService = StationsService.apply(util = HelperUtilities)

  @throws
  private def validate(department: DepartmentRequest): Unit = {
    if (department.code == null) throw new RuntimeException("Department Code id is mandatory")
    if (department.name == null) throw new RuntimeException("Department Name  is mandatory")
    if (department.office_id == 0) throw new RuntimeException("Department Office Id     is mandatory")
  }


  private def populateResponse(result: ResultSet):DepartmentResponse = {
    new DepartmentResponse(result.getInt("id"), result.getString("name"), result.getString("code"), result.getDate("created_on"))
  }

  //todo: create
  @throws
  def create(owner: Integer, department: DepartmentRequest): Unit = {

    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")
    //todo: check to see that  station exists

    validate(department)

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Invalid Authentication")

    val query = "INSERT INTO " + tableName + " (name,code,office_id,author_id)  values ( '" + department.name + "','" + department.code + "','" + department.office_id + "','" + owner + "' ) ";
    conn = getConnection()
    val stmt = conn.createStatement
    val result = stmt executeUpdate (query)
    conn.close()

  }


  //todo: Get All By Account
  def getAll(owner: Integer, offset: Long = 0, limit: Long = 10): Seq[DepartmentResponse] = {
    //todo: verify that owner is not null
    if (owner == null) throw new RuntimeException("Invalid Authentication")

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) throw new RuntimeException("Account does not exist")

    val query = "SELECT A.*,B.name as office_name  FROM   " + tableName + "    INNER JOIN " + OfficeService.tableName + " B ON A.office_id = B.id      offset " + offset + " limit " + limit + "  ";
    conn = getConnection()
    val stmt = conn createStatement
    val result = stmt.executeQuery(query)

    val departmentResponses = new ListBuffer[DepartmentResponse]()

    while (result next()) {
      val officeResponse: DepartmentResponse = populateResponse(result)
      departmentResponses += officeResponse
    }
    conn.close()
    departmentResponses.toSeq
  }




  //todo: get Station by Id
  @throws
  def getById(owner: Integer, officeId: Long): DepartmentResponse = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val query = "SELECT A.* FROM   " + tableName + "  A INNER JOIN " + OfficeService.tableName + " B ON A.office_id = B.id  WHERE  A.id ='" + officeId + "' ";

    conn = getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) {
      val response: DepartmentResponse = populateResponse(result)
      response
    } else
      throw new RuntimeException("Record does not exist in the database")
  }


  //todo: Archive Station
  def Archive(owner: Integer, departmentId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))


    val departmentResponse: DepartmentResponse = getById(owner, departmentId)

    if (departmentResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ARCHIVED' where id='" + departmentId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
      conn.close()
    }

  }


  //todo: Activate
  def Activate(owner: Integer, departmentId: Long): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val account: AccountResponse = AccountService.get(owner);
    if (account == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Account does not exist"))

    val departmentResponse: DepartmentResponse = getById(owner, departmentId)

    if (departmentResponse == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Record does not exist in the database"))
    else {
      var query = "UPDATE    " + tableName + "   SET status='ARCHIVED' where id='" + departmentId + "' ";
      conn = getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
      conn.close()
    }

  }


  //todo: check if kiosk exists
  def checkIfKioskExists(accountId: Integer, reference_id: String): Boolean = {
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

object DepartmentService {
  def apply(stationsService: StationsService): DepartmentService = new DepartmentService(stationsService)
}





//object DepartmentService extends DepartmentService
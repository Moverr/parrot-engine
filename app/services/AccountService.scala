package services


import entities.requests.accounts.AccountRequest
import entities.responses.accounts.AccountResponse
import play.api.db.DB
import play.api.libs.json.Json
import utils.HelperUtilities


//////
import play.api.Play.current
import play.api.mvc.Results._
///////


class AccountService {
  var conn = DB.getConnection()
  val tableName = "\"default\".account"

  //todo: create
  def create(owner: Integer, account: AccountRequest): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val external_id = HelperUtilities.randomStringGenerator(20)
    if (checkIfAccountExists(owner) == true) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Account already created for user"))
    } else {

      var query = "INSERT INTO  " + tableName + " (owner,name,external_id,author_id)  values ('" + owner + "','" + account.name + "','" + external_id + "','" + owner + "') ";
      conn = DB getConnection()
      val stmt = conn createStatement
      var result = stmt executeUpdate (query)
    }


  }

  def checkIfAccountExists(owner: Integer): Boolean = {
    var query = "SELECT * FROM   \"default\".account WHERE owner = " + owner + "    ) ";
    conn = DB getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)
    if (result.next()) true else false
  }

  def get(owner: Integer): AccountResponse = {
    var query = "SELECT * FROM   \"default\".account WHERE owner = " + owner + " ";
    conn = DB getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)


    var account = new AccountResponse(0, "");
    if (result.next()) {

      val accountName = result.getString("name");
      val accountId = result.getInt("id")
      account = new AccountResponse(accountId, accountName);
    }
    account
  }

}

object AccountService extends AccountService{
  def apply(): AccountService = new AccountService()
}

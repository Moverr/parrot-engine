package services

import controllers.v1.AuthController.{BadRequest, conn}
import entities.requests.accounts.AccountReqquest
import entities.responses.accounts.AccountResponse
import play.api.db.DB
import play.api.libs.json.{Json, Writes}
import utils.HelperUtilities


//////
import play.api.Play.current
///////


class AccountService {
  //todo: create
  def create(owner: Integer, account: AccountReqquest): Unit = {
    //todo: verify that owner is not null
    if (owner == null) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Authentication"))

    val external_id = HelperUtilities.randomStringGenerator(20)
    if (checkIfAccountExists(owner) == true) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Account already created for user"))
    } else {
      var query = "INSERT INTO  \"default\".account (owner,name,external_id,author_id)  values ('" + owner + "','" + account.name + "','" + external_id + "','" + owner + "') ";
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
    var query = "SELECT * FROM   \"default\".account WHERE owner = " + owner + "    ) ";
    conn = DB getConnection()
    val stmt = conn createStatement
    var result = stmt.executeQuery(query)

    implicit val response = new Writes[AccountResponse] {
      def writes(_account: AccountResponse) = Json.obj(
        "name" -> _account.name
      )
    }

    var account = new AccountResponse("");
    if (result.next()) {

      val accountName = result.getString("name");
      account = new AccountResponse(accountName);
    }
    account
  }

}

object AccountService extends AccountService

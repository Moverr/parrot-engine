package services

import controllers.v1.AuthController.{BadRequest, conn}
import entities.requests.accounts.AccountReqquest
import play.api.db.DB
import play.api.libs.json.Json
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


    var query = "INSERT INTO  \"default\".account (owner,name,external_id,author_id)  values ('" + owner + "','" + account.name + "','" + external_id + "','" + owner + "') ";
    conn = DB getConnection()
    val stmt = conn createStatement
    var result = stmt executeUpdate (query)

  }

  def get(): Unit = {

  }

}

object AccountService extends AccountService

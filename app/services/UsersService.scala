package app.services

import java.sql.ResultSet

import app.entities.requests.RegistrationRequest
import controllers.v1.AuthController.{BadRequest, conn}
import play.api.libs.json.Json


//////
import play.api.Play.current
import play.api.db._
///////


class UsersService extends UserServiceTrait {

  def register(registrationRequest: RegistrationRequest): Boolean = {
    if (registrationRequest.email.isEmpty()) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Email is Mandatory"))
    }
    else if (registrationRequest.password.isEmpty()) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Password is Mandatory"))
    } else {

    }

    false
  }


  def fetchUserByEmailAndPassword(email: String, password: String): ResultSet = {

    var query = "SELECT * FROM  \"default\".users as A " +
      "WHERE " +
      " A.username LIKE \'" + email + "\' " +
      "AND" +
      " A.password LIKE \'" + password + "\' ";
    print("STR: " + query)
    conn = DB.getConnection()
    val stmt = conn.createStatement
    var resultSet = stmt.executeQuery(query)
    resultSet

  }


  def ValidateIfUserExists(email: String, password: String): Boolean = {

    var query = "SELECT * FROM  \"default\".users as A " +
      "WHERE " +
      " A.username LIKE \'" + email + "\' ";


    conn = DB.getConnection()

    val stmt = conn.createStatement

    print("STR: " + query)

    var resultSet = stmt.executeQuery(query)


    if (resultSet.next()) {

      true

    } else {
      false
    }


  }


  override def createUser(registrationRequest:RegistrationRequest): Unit = {

  }

  override def list(offset: Int, limit: Int): Unit = {

  }

  override def get(id: Int): Unit = {

  }

  override def search(query: String, offset: Int, limit: Int): Unit = {

  }

  override def populateResponse(): Unit = {

  }

  override def populateEntity(): Unit = {

  }


}

object UsersService extends UsersService
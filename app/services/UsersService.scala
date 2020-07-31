package app.services

import java.sql.ResultSet
import java.util.Date

import app.entities.requests.{AuthenticationRequest, RegistrationRequest}
import app.entities.responses.AuthResponse
import controllers.v1.AuthController.{BadRequest, conn}
import entities.responses.RegistrationResponse
import play.api.libs.json.Json
import utils.{HelperUtilities, PasswordHashing}


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
    }

    false
  }

  def login(authRequest: AuthenticationRequest): AuthResponse = {

    val resultSet = fetchUserByEmailAndPassword(authRequest.username, PasswordHashing.encryptPassword(authRequest.password));
    // if resulset is not empty
    if (resultSet.next()) {
      val id: Integer = resultSet.getInt("id")
      val username = resultSet.getString("username")
//      val password = resultSet.getString("password")
      val createdOn = resultSet.getDate("created_on")
      val token = HelperUtilities.convertToBasicAuth(username , authRequest.password)
      new AuthResponse(id, username, token, createdOn)
    } else null


  }

  def fetchUserByEmailAndPassword(email: String, password: String): ResultSet = {
    var query = "SELECT * FROM  \"default\".users as A " + "WHERE " + " A.username LIKE \'" + email + "\' " + "AND" + " A.password LIKE \'" + password + "\' ";
    print("STR: " + query)
    conn = DB getConnection()
    val stmt = conn createStatement
    var resultSet = stmt executeQuery (query)
    resultSet
  }


  def ValidateIfUserExists(email: String, password: String): Boolean = {
    var query = "SELECT * FROM  \"default\".users as A " + "WHERE " + " A.username LIKE \'" + email + "\' ";
    conn = DB getConnection()
    val stmt = conn.createStatement
    print("STR: " + query)
    var resultSet = stmt executeQuery (query)
    if (resultSet next()) true else false
  }


  override def createUser(registrationRequest: RegistrationRequest): RegistrationResponse = {
    var query = "INSERT INTO  \"default\".users (username,password)  values ('" + registrationRequest.email + "','" + PasswordHashing.encryptPassword(registrationRequest.password) + "') ";
    conn = DB getConnection()
    val stmt = conn createStatement
    var result = stmt executeUpdate (query)
    val response = new RegistrationResponse(1, registrationRequest.email, new Date())
    response
  }

  //todo: validate token and return a User Object
  def validateAuthorization(authentication: String): AuthResponse = {

    val auth = authentication.replace("bearer", "").trim()
    val userNameAndPassword = HelperUtilities.decodeAuth(auth)

    if (userNameAndPassword == null) {
      null
    }
    else {
      val username = userNameAndPassword(0)
      val password = userNameAndPassword(1)
      print("..............KKKK................")
      print("PASSWORD"+password)
      print("..............KKKK................")

      val authRequest = new AuthenticationRequest(username, password)
      val _response = login(authRequest: AuthenticationRequest)
      _response

    }


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
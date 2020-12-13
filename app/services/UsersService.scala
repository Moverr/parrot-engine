package app.services

import java.util.Date

import akka.actor.FSM.Failure
import app.entities.requests.{AuthenticationRequest, RegistrationRequest}
import app.entities.responses.AuthResponse
import controllers.v1.AuthController.{BadRequest, conn}
import entities.responses.RegistrationResponse
import javax.inject.Inject
import play.api.libs.json.Json
import slick.jdbc.PostgresProfile.api._
import tables.Main.{User, databaseConnector, users}
import utils.{HelperUtilities, PasswordHashing}

import scala.concurrent.Future
//import defaults ::

import scala.util.Success
//////
import play.api.Play.current
import play.api.db._
///////
import scala.concurrent.ExecutionContext.Implicits.global

class UsersService @Inject()(util: HelperUtilities) extends UserServiceTrait {


  def register(registrationRequest: RegistrationRequest): Boolean = {


    if (registrationRequest.email.isEmpty()) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Email is Mandatory"))
    }
    else if (registrationRequest.password.isEmpty()) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Password is Mazndatory"))
    }

    false
  }


  //todo: Login User by Username and Password
  def login(authRequest: AuthenticationRequest): AuthResponse = {
    val x = fetchUserByEmailAndPassword(authRequest.username, PasswordHashing.encryptPassword(authRequest.password))
    var _user: User = null

    x.onComplete {
      case Success(s) =>_user = s
      case Failure(exception)=> _user = null
    }
    populateResponse(_user)

  }

  //todo: Get User by Username and Email
  def fetchUserByEmailAndPassword(email: String, password: String): Future[User] = {
    val query = users.filter(p => p.username === email && p.password === password)
    databaseConnector.run(query.result.head)
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

      val authRequest = new AuthenticationRequest(username, password)
      val x = fetchUserByEmailAndPassword(authRequest.username, authRequest.password);

      var _user: User = null

      x.onComplete {
        case Success(s) =>_user = s
        case Failure(exception)=> _user = null
      }

      val _response = populateResponse(_user)
      _response

    }


  }

  private def populateResponse(_user: User) = {

    val id: Long =_user.id
    val username =_user.username
    val password =_user.password
    val createdOn = _user.created_on

    val token = util.convertToBasicAuth(username, password)
    new AuthResponse(id, username, token, createdOn)
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

object UsersService {
  def apply(util: HelperUtilities): UsersService = new UsersService(util)
}


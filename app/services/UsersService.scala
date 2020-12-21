package app.services

import app.entities.requests.{AuthenticationRequest, RegistrationRequest}
import app.entities.responses.AuthResponse
import controllers.v1.AuthController.BadRequest
import entities.responses.RegistrationResponse
import javax.inject.Inject
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import tables.{User, UserTable}
import utils.{HelperUtilities, PasswordHashing}

import scala.concurrent.Future
//import defaults ::
import scala.util.Success
//////


///////
import scala.concurrent.ExecutionContext.Implicits.global

class UsersService @Inject()(

                              @NamedDatabase("orders") ordersDatabase: Database,
                              dbConfigProvider: DatabaseConfigProvider ,
                              util: HelperUtilities)  {


  import dbConfig._
  import profile.api._


  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val table = TableQuery[UserTable]


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
      //case Failure(_) => _user = null

    }
    populateResponse(_user)

  }
  lazy val users = TableQuery[UserTable]


  //todo: Get User by Username and Email
  def fetchUserByEmailAndPassword(email: String, password: String): Future[User] = {

   val query = users.filter(p => p.username === email && p.password === password)
    db.run(query.result.head)

    null
  }



  def ValidateIfUserExists(email: String, password: String): Boolean = {
 /*   var query = "SELECT * FROM  \"default\".users as A " + "WHERE " + " A.username LIKE \'" + email + "\' ";
    conn = DB getConnection()
    val stmt = conn.createStatement
    print("STR: " + query)
    var resultSet = stmt executeQuery (query)
    if (resultSet next()) true else false
    */
    false
  }


    def createUser(registrationRequest: RegistrationRequest): RegistrationResponse = {
   /* var query = "INSERT INTO  \"default\".users (username,password)  values ('" + registrationRequest.email + "','" + PasswordHashing.encryptPassword(registrationRequest.password) + "') ";
    conn = DB getConnection()
    val stmt = conn createStatement
    var result = stmt executeUpdate (query)
    val response = new RegistrationResponse(1, registrationRequest.email, new Date())
    response */
    null
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
        //case Failure(_)=> _user = null
      }
      val _response = populateResponse(_user)
      _response

    }


  }

    def populateResponse(_user: User) = {

    val id: Long =_user.id
    val username =_user.username
    val password =_user.password
    val createdOn = _user.created_on

    val token = util.convertToBasicAuth(username, password)
    new AuthResponse(id, username, token, createdOn.toString("yyyy-mm-dd"))
  }






}

object UsersService {
  def apply(ordersDatabase: Database, dbConfigProvider: DatabaseConfigProvider, util: HelperUtilities): UsersService = new UsersService(ordersDatabase, dbConfigProvider, util)
 }


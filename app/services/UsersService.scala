
package app.services

import app.entities.requests.{AuthenticationRequest, RegistrationRequest}
import app.entities.responses.AuthResponse
import controllers.v1.AuthController.BadRequest
import entities.responses.RegistrationResponse
import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import services.traits.TUserService
import slick.jdbc.JdbcProfile
import slick.lifted
import tables.User
import utils.{HelperUtilities, PasswordHashing}

import scala.concurrent.Future
//import defaults ::
import scala.util.Success
//////
//import defaults ::
import implicits.CustomColumnTypes._

import slick.jdbc.PostgresProfile.api._
///////
import scala.concurrent.ExecutionContext.Implicits.global
@Singleton
class UsersService @Inject()(
                              dbConfigProvider: DatabaseConfigProvider ,
                              util: HelperUtilities)  extends TUserService {



  import dbConfig._
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  val users = lifted.TableQuery[UserTable]



  override def register(registrationRequest: RegistrationRequest): Boolean = {


    if (registrationRequest.email.isEmpty()) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Email is Mandatory"))
    }
    else if (registrationRequest.password.isEmpty()) {
      BadRequest(Json.obj("status" -> "Error", "message" -> "Password is Mazndatory"))
    }

    false
  }


  //todo: Login User by Username and Password
  override def login(authRequest: AuthenticationRequest): AuthResponse = {
    val x = fetchUserByEmailAndPassword(authRequest.username, PasswordHashing.encryptPassword(authRequest.password))
    var _user: User = null

    x.onComplete {
      case Success(s) =>_user = s
      //case Failure(_) => _user = null

    }
    populateResponse(_user)

  }



  //todo: Get User by Username and Email
  override def fetchUserByEmailAndPassword(email: String, password: String): Future[User] = {

    val query = users.filter(p => p.username === email && p.password === password)
    db.run(query.result.head)

    null
  }



  override def ValidateIfUserExists(email: String, password: String): Boolean = {
    /*   var query = "SELECT * FROM  \"default\".users as A " + "WHERE " + " A.username LIKE \'" + email + "\' ";
       conn = DB getConnection()
       val stmt = conn.createStatement
       print("STR: " + query)
       var resultSet = stmt executeQuery (query)
       if (resultSet next()) true else false
       */
    false
  }


  override def createUser(registrationRequest: RegistrationRequest): RegistrationResponse = {
    /* var query = "INSERT INTO  \"default\".users (username,password)  values ('" + registrationRequest.email + "','" + PasswordHashing.encryptPassword(registrationRequest.password) + "') ";
     conn = DB getConnection()
     val stmt = conn createStatement
     var result = stmt executeUpdate (query)
     val response = new RegistrationResponse(1, registrationRequest.email, new Date())
     response */
    null
  }

  //todo: validate token and return a User Object
  override def validateAuthorization(authentication: String): AuthResponse = {

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

    def populateResponse(_user: User):AuthResponse = {

    val id: Long =_user.id
    val username =_user.username
    val password =_user.password
    val createdOn = _user.created_on

    val token = util.convertToBasicAuth(username, password)
    val response = new AuthResponse(id, username, token, createdOn.toString("yyyy-mm-dd"))
    response
  }



  private class UserTable(tag: Tag) extends Table[User](tag, "user") {
    override def * = (id, username, password, author_id, created_on, updated_by, updated_on.?) <> (User.tupled, User.unapply)

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def username = column[String]("username")

    def password = column[String]("password")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("dateupdated")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")
  }

}


package controllers.v1

import app.entities.requests.{AuthForm, AuthenticationRequest, LoginRequest, SocialAuthentication}
import app.entities.responses.{AuthResponse, RoleResponse, UserResponse}
import app.utils.StatusEnums
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.json.Json

import scala.util.parsing.json._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._

import play.api.db._
import play.api.Play.current

object AuthController extends Controller {


    //todo: auth login
    def login() = Action {
        implicit request =>
            val authRequest: AuthenticationRequest = AuthForm.form.bindFromRequest.get

            implicit val roleResponserites = new Writes[AuthenticationRequest] {
                def writes(role: AuthenticationRequest) = Json.obj(
                    "name" -> role.email,
                    "password" -> role.password
                )
            }

            //todo: select from db where user name and password = xx s
            val conn = DB.getConnection()
            var  jsson = Json.toJson("");
            try {
                val stmt = conn.createStatement
                var query = "SELECT * FROM  \"default\".users as A " +
                        "WHERE " +
                        " A.username LIKE \'" + authRequest.email + "\' " +
                        "AND" +
                        " A.password LIKE \'" + authRequest.password + "\' ";

                print("STR: " + query)

                val resultSet = stmt.executeQuery(query)
                var username = "";
                var password = "";
                while (resultSet.next()) {
                      username = resultSet.getString("username")
                    password = resultSet.getString("password")

                }
                if(username.length() > 0 && password.length() > 0 ){
                      jsson = Json.toJson(authRequest)
                }


            } finally {

                conn.close();
            }




            Ok(jsson)


    }

    def index = Action {
        request =>
            val json = request.body.asJson.get

            val roleResponse = new RoleResponse(null, null);

            implicit val roleResponserites = new Writes[RoleResponse] {
                def writes(role: RoleResponse) = Json.obj(
                    "name" -> role.name,
                    "code" -> role.code
                )
            }


            val userResponse = new UserResponse(1, StatusEnums.ACTIVE, "Muyinda", "Rogers", roleResponse, "moverr@gmail.com", "utc", "ee", null, "0779820962")


            implicit val userResponseWrites = new Writes[UserResponse] {
                def writes(user: UserResponse) = Json.obj(
                    "id" -> user.id,
                    "status" -> user.status,
                    "firstname" -> user.firstname,
                    "lastname" -> user.lastname,
                    "role" -> user.role,
                    "email" -> user.email,
                    "timezone" -> user.timezone,
                    "locale" -> user.locale,
                    "avatar_url" -> user.avatar_url,
                    "phone_number" -> user.phone_number

                )
            }


            val authResponse = new AuthResponse("token", userResponse)


            implicit val userWrites = new Writes[AuthResponse] {
                def writes(auth: AuthResponse) = Json.obj(
                    "token" -> auth.token,
                    "user" -> auth.user
                )
            }


            val jsson = Json.toJson(authResponse)

            //todo: select from database and return this object

            Ok(jsson)
    }


    def socialAuthenticate(socialauth: SocialAuthentication): Unit = {
    }

    def deactivate(): Unit = {
    }

    def resetPassword(): Unit = {

    }

    //todo: Register User to the System : Self registration
    def register(): Unit = {

    }


}

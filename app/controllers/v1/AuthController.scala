package controllers.v1

import app.entities.requests.{AuthForm, AuthenticationRequest, LoginRequest, RegistrationForm, RegistrationRequest, SocialAuthentication}
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
                    "username" -> role.username,
                    "password" -> role.password
                )
            }

            //todo: select from db where user name and password = xx s
            val conn = DB.getConnection()
            var jsson = Json.toJson("");
            try {
                val stmt = conn.createStatement
                var query = "SELECT * FROM  \"default\".users as A " +
                        "WHERE " +
                        " A.username LIKE \'" + authRequest.username + "\' " +
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
                if (username.length() > 0 && password.length() > 0) {
                    jsson = Json.toJson(authRequest)
                }


            } finally {

                conn.close();
            }


            Ok(jsson)


    }

    def register() = Action {

        implicit request =>
            val registrationRequest: RegistrationRequest = RegistrationForm.form.bindFromRequest.get

        Ok("Lord is merciful");
    }

    def socialAuthenticate(socialauth: SocialAuthentication): Unit = {
    }

    def deactivate(): Unit = {
    }

    def resetPassword(): Unit = {

    }


}

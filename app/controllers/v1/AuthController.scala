package controllers.v1

import java.sql.ResultSet

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
    var conn = DB.getConnection()

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

            var jsson = Json.toJson("");

            var query = "SELECT * FROM  \"default\".users as A " +
                    "WHERE " +
                    " A.username LIKE \'" + authRequest.username + "\' " +
                    "AND" +
                    " A.password LIKE \'" + authRequest.password + "\' ";

            try {


                val resultSet = fetchUserByEmailAndPassword(authRequest.username, authRequest.password);

                if (resultSet.next()) {
                    //todo: Populate a basic JWT Token
                   /* while (resultSet.next()) {
                        username = resultSet.getString("username")
                        password = resultSet.getString("password")

                    }
                    if (username.length() > 0 && password.length() > 0) {
                        jsson = Json.toJson(authRequest)
                    } */
                }else{
                    Unauthorized(Json.obj("status" -> "Un Authorized", "message" -> "User is not Authorized"))
                }


            }
            finally {
                conn.close()
            }

            Unauthorized(Json.obj("status" -> "Un Authorized", "message" -> "User is not Authorized"))
            //Ok(jsson)


    }


    def register() = Action {

        implicit request =>
            val registrationRequest: RegistrationRequest = RegistrationForm.form.bindFromRequest.get

            //todo: check for nulls
            if (registrationRequest.email.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Email is Mandatory"))
            }

            else if (registrationRequest.firstname.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Firstname is Mandatory"))
            }

            else if (registrationRequest.lastname.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Lastname is Mandatory"))
            }

            else if (registrationRequest.password.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Password is Mandatory"))
            } else {
                //todo: select from db wheren username like email..

                BadRequest(Json.obj("status" -> "Error", "message" -> "Password is Mandatory"))
            }


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
        val resultSet = stmt.executeQuery(query)
        resultSet


    }

    def ExecuteQuerySelect(query: String) = {


        conn = DB.getConnection()

        val stmt = conn.createStatement

        print("STR: " + query)

        val resultSet = stmt.executeQuery(query)
        resultSet


    }


}

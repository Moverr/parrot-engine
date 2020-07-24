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

import app.utils.JwtUtility




object AuthController extends Controller {
    var conn = DB.getConnection()

/*
    val JwtSecretKey = "secretKey"
    val JwtSecretAlgo = "HS256"

    def createToken(payload: String): String = {
        val header = JwtHeader(JwtSecretAlgo)
        val claimsSet = JwtClaimsSet(payload)
        JsonWebToken(header, claimsSet, JwtSecretKey)
    }


    def isValidToken(jwtToken: String): Boolean =
        JsonWebToken.validate(jwtToken, JwtSecretKey)
    def decodePayload(jwtToken: String): Option[String] =
        jwtToken match {
            case JsonWebToken(header, claimsSet, signature) => Option(claimsSet.asJsonString)
            case _                                          => None
        }

*/



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
                  // BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "User is not Authorized"))
//                    val token = Jwt.encode("""{"user":1}""", "secretKey", JwtAlgorithm.HS256)
//                    token


                    val token =  JwtUtility createToken("movers")

                   // val token = createToken("mover")
                    Ok(token)

                }else{
                      Unauthorized(Json.obj("status" -> "Un Authorized", "message" -> " Bingo  User is not Authorized"))
                }








    }


    def register() = Action {

        implicit request =>
            val registrationRequest: RegistrationRequest = RegistrationForm.form.bindFromRequest.get

            //todo: check for nulls
            if (registrationRequest.email.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Email is Mandatory"))
            }
            else if (registrationRequest.password.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Password is Mandatory"))
            } else {
                //todo: username and password

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

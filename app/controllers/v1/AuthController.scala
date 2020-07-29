package controllers.v1

import app.entities.requests.{AuthForm, AuthenticationRequest, RegistrationForm, RegistrationRequest}
import app.entities.responses.AuthResponse
import app.services.UsersService
import play.api.libs.json.Json
import play.api.mvc._

//////
import play.api.Play.current
import play.api.db._
import play.api.libs.json._
///////


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


            var response: AuthResponse = UsersService login (authRequest)
            if (response == null) Unauthorized(Json.obj("status" -> "Un Authorized", "message" -> " Bingo  User is not Authorized"))
            else {
                //  val  token =  JwtUtility createToken(AuthResponse.username+":"+password)
                Ok(Json.obj("status" -> "Ok", "username" -> response.username, "token" -> response.token))
            }


    }


    //todo: Register

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
                var userExists: Boolean = UsersService ValidateIfUserExists(registrationRequest.email, registrationRequest.password)
                if (userExists == true)
                    BadRequest(Json.obj("code" -> 400, "status" -> "Badrequest", "message" -> "User already registered to the system "))
                else {
                    UsersService createUser registrationRequest
                    //todo: create a user and move on
                    Ok(Json.obj("code" -> 200, "status" -> "Success", "message" -> "User Created"))
                }
            }

    }


}

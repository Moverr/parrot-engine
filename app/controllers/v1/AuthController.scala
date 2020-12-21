package controllers.v1

import app.entities.requests.{AuthForm, AuthenticationRequest, RegistrationForm, RegistrationRequest}
import app.entities.responses.AuthResponse
import app.services.UsersService
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

//////
import play.api.libs.json._
///////


class  AuthController @Inject()(userService: UsersService) extends Controller {

   

    def login() = Action {
        implicit request =>
            val authRequest: AuthenticationRequest = AuthForm.form.bindFromRequest.get

            implicit val roleResponserites = new Writes[AuthenticationRequest] {
                def writes(role: AuthenticationRequest) = Json.obj(
                    "username" -> role.username,
                    "password" -> role.password
                )
            }

            var response: AuthResponse = userService login (authRequest)
            if (response == null) Unauthorized(Json.obj("status" -> "Un Authorized", "message" -> " Bingo  User is not Authorized"))
            else {
                implicit val resposnse = new Writes[AuthResponse] {
                    def writes(_auth: AuthResponse) = Json.obj(
                        "id" -> _auth.id.toString
                        , "username" -> _auth.username
                        , "token" -> _auth.token
                        , "created_on" -> _auth.dateCreated
                    )
                }

                //                Ok(Json.obj("status" -> "Ok", "username" -> response.username, "token" -> response.token))
                Ok(Json.toJson(response))
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
                var userExists: Boolean = userService ValidateIfUserExists(registrationRequest.email, registrationRequest.password)
                if (userExists == true)
                    BadRequest(Json.obj("code" -> 400, "status" -> "Badrequest", "message" -> "User already registered to the system "))
                else {
                    userService createUser registrationRequest
                    Ok(Json.obj("code" -> 200, "status" -> "Success", "message" -> "User Created"))
                }
            }

    }


}

object AuthController extends Controller{
    def apply(userService: UsersService): AuthController = new AuthController(userService)
}

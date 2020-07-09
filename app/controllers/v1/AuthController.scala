package controllers.v1

import app.entities.requests.{LoginRequest, SocialAuthentication}
import app.entities.responses.{AuthResponse, UserResponse}
import app.utils.StatusEnums
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.json.Json

import scala.util.parsing.json._
import play.api.libs.json._


object AuthController extends Controller {



    def index = Action {
        request =>
            val json = request.body.asJson.get




            val userResponse = new UserResponse(1,StatusEnums.ACTIVE,"Muyinda","Rogers",null,"moverr@gmail.com","utc","ee",null,"0779820962")



            implicit val userResponseWrites = new Writes[UserResponse] {
                def writes(user: UserResponse) = Json.obj(
                    "id" -> user.id,
                    "status" -> user.status
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

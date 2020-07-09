package controllers.v1

import app.entities.requests.{LoginRequest, SocialAuthentication}
import app.entities.responses.{AuthResponse, UserResponse}

import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._
import play.api.libs.json._


object AuthController extends Controller {

    //    def authenticate = Action{
    //        request =>
    //            val json = request.body.asJson.get
    //            val userResponse = new AuthResponse("Trust in the lord", null)
    //            ok(userResponse)
    //    }
    //    def authenticate(login: LoginRequest): AuthResponse = {
    //        val userResponse = new AuthResponse("Trust in the lord", null)
    //        return userResponse
    //    }
    def index = Action {
        request =>
            val json = request.body.asJson.get
            val userResponse = new AuthResponse("Trust in the lord", null)

           // implicit val wformats = DefaultFormats

//            implicit val formats =  Json.format[userResponse]
//
//            val jsonString = write(userResponse)

            val xt = Json.parse(userResponse);
            Ok("This is WOrking")
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

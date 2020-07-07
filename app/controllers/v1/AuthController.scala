package app.controllers.v1

import app.entities.requests.{LoginRequest, SocialAuthentication}
import javax.inject.Inject
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._



object AuthController extends Controller {

    def authenticate(login:LoginRequest): Unit ={
    }

    def socialAuthenticate(socialauth:SocialAuthentication): Unit ={
    }

    def deactivate(): Unit ={
    }

    def resetPassword(): Unit ={

    }

    //todo: Register User to the System : Self registration
    def register(): Unit ={

    }
}

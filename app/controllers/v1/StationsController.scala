package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.stations.StationRequest
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.StationsService
import utils.HelperUtilities

//todo: deploy to the server and move
//todo: create stations and move on
object StationsController extends Controller {
    def create = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                val stationRequest: StationRequest = StationRequest.form.bindFromRequest.get
                StationsService.create(authResponse.id, stationRequest)
                Ok(HelperUtilities successResponse ("Record saved succesfully"))
            }


    }

    def getAll = Action {
        Ok("List all Stations")
    }

    def get = Action {
        Ok("Get By Id")
    }

    def archive = Action {
        Ok("Archivee Station")
    }

    def refresh = Action {
        Ok("Refresh Station to Active")
    }

}

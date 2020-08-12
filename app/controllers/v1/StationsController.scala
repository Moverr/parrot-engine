package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.stations.StationRequest
import entities.responses.stations.StationResponse
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}
import services.StationsService
import utils.HelperUtilities

//todo: deploy to the server and move
//todo: create stations and move on
object StationsController extends Controller {

    implicit val resposnse = new Writes[StationResponse] {
        def writes(_account: StationResponse) = Json.obj(
            "id" -> _account.id.toString,
            "name" -> _account.name,
            "code" -> _account.code
        )
    }


    def create = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                try {
                    val stationRequest: StationRequest = StationRequest.form.bindFromRequest.get
                    StationsService.create(authResponse.id, stationRequest)
                    Ok(HelperUtilities successResponse ("Record saved succesfully"))
                }
                catch {
                    case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                }

            }

    }

    def getAll = Action {
        implicit request =>
            val limit: Long =
                request.getQueryString("limit").map(_.toLong).getOrElse(50)
            val offset: Long =
                request.getQueryString("offset").map(_.toLong).getOrElse(0)

            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {
                val response: Seq[StationResponse] = StationsService.getAll(authResponse.id, offset, limit)
                Ok(Json.toJson(response))
            }


    }

    //todo:update the station name

    def show(id: Long) = Action {
        implicit request =>
            val stationId: Long = id

            val authorization = request.headers.get("Authorization").get
            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))

            else {
                if (stationId == 0) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Station ID "))

                else {
                    try {
                        val response: StationResponse = StationsService.getById(authResponse.id, stationId)
                        Ok(Json.toJson(response))
                    }
                    catch {
                        case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                    }
                }
            }


    }

    def archive = Action {

        implicit request =>
            val stationId: Long =
                request.getQueryString("limit").map(_.toLong).getOrElse(0)
            Ok("Get By Id")


    }

    def activate = Action {

        implicit request =>
            val stationId: Long =
                request.getQueryString("limit").map(_.toLong).getOrElse(0)
            Ok("Refresh Station to Active")
    }

}

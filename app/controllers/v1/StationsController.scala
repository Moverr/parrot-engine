package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.stations.StationRequest
import entities.responses.stations.StationResponse
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}
import services.StationsService
import utils.HelperUtilities
//import defaults ::
//////

//todo: deploy to the server and move
//todo: create stations and move on


object  StationsController     extends Controller     {

    private implicit val ordersDatabase: Database = null
    private implicit val  dbConfigProvider: DatabaseConfigProvider = null


    implicit  def userService: UsersService = new UsersService(dbConfigProvider,HelperUtilities)
    implicit  def stationService =  StationsService.apply( HelperUtilities)



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

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                try {
                    val stationRequest: StationRequest = StationRequest.form.bindFromRequest.get
                    stationService create(authResponse.id.toInt, stationRequest)
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
                request.getQueryString("limit").map(_.toLong).getOrElse(HelperUtilities defaultLimit)
            val offset: Long =
                request.getQueryString("offset").map(_.toLong).getOrElse(HelperUtilities defaultOffset)

            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                val response: Seq[StationResponse] = stationService getAll(authResponse id, offset, limit)
                Ok(Json.toJson(response))
            }


    }

    //todo:update the station name

    def show(id: Long) = Action {
        implicit request =>
            val stationId: Long = id

            val authorization = request.headers.get("Authorization").get
            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))

            else {
                if (stationId == 0) BadRequest(Json obj("status" -> "Error", "message" -> "Invalid Station ID "))

                else {
                    try {
                        val response: StationResponse = stationService getById(authResponse.id.toInt, stationId)
                        Ok(Json.toJson(response))
                    }
                    catch {
                        case e: RuntimeException => {

                            var message = ""
                            if (e.getMessage != null) message = e getMessage

                            BadRequest(Json obj("status" -> "Error", "message" -> message))
                        }
//                        case _ =>    BadRequest(Json.obj("status" -> "Error", "message" -> "Something went wrong check input data"))

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


package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.kiosks.KioskRequest
import entities.responses.kiosks.KioskResponse
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}
import services.{KioskService, StationsService}
import utils.HelperUtilities

object  KioskController    extends Controller {


    private implicit val ordersDatabase: Database = null
    private implicit val  dbConfigProvider: DatabaseConfigProvider = null

    implicit  def userService: UsersService = new UsersService(dbConfigProvider,HelperUtilities)

    implicit  def kioskService = KioskService.apply(StationsService.apply(HelperUtilities))


    implicit val resposnse = new Writes[KioskResponse] {
        def writes(_kiosk: KioskResponse) = Json.obj(
            "id" -> _kiosk.id
            , "reference_id" -> _kiosk.reference_id
            , "details" -> _kiosk.details
            , "device_token" -> _kiosk.device_token
            , "created_on" -> _kiosk.created_on
        )
    }


    def create = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                try{
                    val kioskRequest: KioskRequest = KioskRequest.form.bindFromRequest.get
                    kioskService.create(authResponse.id.toInt, kioskRequest)
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

            val stationId:Long =
                request.getQueryString("stationid").map(_.toLong).getOrElse(0)

            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {
                if(stationId > 0 ){
                    val response: Seq[KioskResponse] = kioskService.getAllByStation(authResponse.id.toInt,stationId, offset, limit)
                    Ok(Json.toJson(response))
                }else{
                    val response: Seq[KioskResponse] = kioskService.getAll(authResponse.id.toInt, offset, limit)
                    Ok(Json.toJson(response))
                }

            }


    }

    def show(id: Long) = Action {
        implicit request =>
            val kioskId: Long = id

            val authorization = request.headers.get("Authorization").get
            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))

            else {
                if (kioskId == 0) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Station ID "))

                else {
                    try {
                        val response: KioskResponse = kioskService.getById(authResponse.id.toInt, kioskId)
                        Ok(Json.toJson(response))
                    }
                    catch {
                        case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                    }
                }
            }

    }

}

package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.kiosks.KioskRequest
import entities.responses.kiosks.KioskResponse
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}
import services.KioskService
import utils.HelperUtilities

object KioskController extends Controller {

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

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                val kioskRequest: KioskRequest = KioskRequest.form.bindFromRequest.get
                KioskService.create(authResponse.id, kioskRequest)
                Ok(HelperUtilities successResponse ("Record saved succesfully"))

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

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {
                if(stationId > 0 ){
                    val response: Seq[KioskResponse] = KioskService.getAll(authResponse.id,stationId, offset, limit)
                    Ok(Json.toJson(response))
                }else{
                    val response: Seq[KioskResponse] = KioskService.getAll(authResponse.id, offset, limit)
                    Ok(Json.toJson(response))
                }

            }


    }

    def show(id: Long) = Action {
        implicit request =>
            val kioskId: Long = id

            val authorization = request.headers.get("Authorization").get
            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))

            else {
                if (kioskId == 0) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Station ID "))

                else {
                    try {
                        val response: KioskResponse = KioskService.getById(authResponse.id, kioskId)
                        Ok(Json.toJson(response))
                    }
                    catch {
                        case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                    }
                }
            }

    }

    //todo: create

    //list

    //getby id

    //deactivate
    //activate
    //update
}

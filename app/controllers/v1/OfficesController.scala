package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.offices.OfficeRequest
import entities.responses.offices.OfficeResponse
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}
import services.OfficeService
import utils.HelperUtilities

object OfficesController extends Controller {
    implicit val resposnse = new Writes[OfficeResponse] {
        def writes(_office: OfficeResponse) = Json.obj(
            "id" -> _office.id
            , "name" -> _office.name
            , "parent_office" -> _office.parent_office
            , "created_on" -> _office.created_on
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
                    val officeRequest: OfficeRequest = OfficeRequest.form.bindFromRequest.get
                    OfficeService.create(authResponse.id, officeRequest)
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

                val response: Seq[OfficeResponse] = OfficeService.getAll(authResponse.id, offset, limit)
                Ok(Json.toJson(response))
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
                        val response: OfficeResponse = OfficeService.getById(authResponse.id, kioskId)
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

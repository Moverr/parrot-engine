package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.offices.{OfficeAssignRequest, OfficeRequest}
import entities.responses.offices.OfficeResponse
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}
import services.{OfficeService, StationsService}
import utils.HelperUtilities

object OfficesController extends Controller {
    implicit  def userService =  UsersService.apply( HelperUtilities)

    implicit  def officeService = OfficeService.apply(StationsService.apply(HelperUtilities))

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

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {
                try {
                    val officeRequest: OfficeRequest = OfficeRequest.form.bindFromRequest.get
                    officeService.create(authResponse.id, officeRequest)
                    Ok(HelperUtilities successResponse ("Record saved succesfully"))
                }
                catch {
                    case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                }
            }
    }


    def assign = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {
                try {
                    val officeRequest: OfficeAssignRequest = OfficeAssignRequest.form.bindFromRequest.get
                    officeService.assign(authResponse.id, officeRequest)
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
                request.getQueryString("limit").map(_.toLong).getOrElse(HelperUtilities.defaultLimit)
            val offset: Long =
                request.getQueryString("offset").map(_.toLong).getOrElse(HelperUtilities.defaultOffset)

            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                val response: Seq[OfficeResponse] = officeService.getAll(authResponse.id, offset, limit)
                Ok(Json.toJson(response))
            }
    }

    def show(id: Long) = Action {
        implicit request =>
            val officeId: Long = id

            val authorization = request.headers.get("Authorization").get
            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))

            else {
                if (officeId == 0) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Station ID "))

                else {
                    try {
                        val response: OfficeResponse = officeService.getById(authResponse.id, officeId)
                        Ok(Json.toJson(response))
                    }
                    catch {
                        case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                    }
                }
            }

    }



}

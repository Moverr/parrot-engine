package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.departments.DepartmentRequest
import entities.responses.departments.DepartmentResponse
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import services.{DepartmentService, OfficeService, StationsService}
import utils.HelperUtilities


object DepartmentsController extends Controller  {

    implicit  def userService =  UsersService.apply( HelperUtilities)
    implicit  def departmentService = DepartmentService.apply(StationsService.apply(HelperUtilities),OfficeService.apply(StationsService.apply(HelperUtilities)))


    implicit val resposnse = new Writes[DepartmentResponse] {
        def writes(_department: DepartmentResponse) = Json.obj(
            "id" -> _department.id
            , "name" -> _department.name
            , "code" -> _department.code
            , "created_on" -> _department.created_on
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
                    val departmentRequest: DepartmentRequest = DepartmentRequest.form.bindFromRequest.get
                    departmentService create(authResponse.id, departmentRequest)
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
                val response: Seq[DepartmentResponse] = departmentService getAll(authResponse.id, offset, limit)
                Ok(Json.toJson(response))
            }


    }

    def show(id: Long) = Action {
        implicit request =>
            val departmentId: Long = id

            val authorization = request.headers.get("Authorization").get
            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))

            else {
                if (departmentId == 0) BadRequest(Json.obj("status" -> "Error", "message" -> "Invalid Station ID "))

                else {
                    try {
                        val response: DepartmentResponse = departmentService getById(authResponse.id, departmentId)
                        Ok(Json.toJson(response))
                    }
                    catch {
                        case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                    }
                }
            }

    }
}

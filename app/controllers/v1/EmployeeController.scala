package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.employee.EmployeeRequest
import entities.responses.employee.EmployeeResponse
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import services.{EmployeeService, StationsService}
import utils.HelperUtilities

//////
///////




//todo: working on employee and moving on
object EmployeeController     extends Controller {


    private implicit val ordersDatabase: Database = null
    private implicit val  dbConfigProvider: DatabaseConfigProvider = null

    implicit  def userService: UsersService = new UsersService(dbConfigProvider,HelperUtilities)

    implicit  def employeeService = EmployeeService(StationsService.apply(HelperUtilities))

    implicit val resposnse = new Writes[EmployeeResponse] {
        def writes(_employee: EmployeeResponse) = Json.obj(
            "id" -> _employee.id
            , "names" -> _employee.names
            , "gender" -> _employee.gender
            , "created_on" -> _employee.created_on
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
                    val employeeRequest: EmployeeRequest = EmployeeRequest.form.bindFromRequest.get
                    employeeService.create(authResponse.id.toInt, employeeRequest)
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

            val stationId: Long =
                request.getQueryString("stationid").map(_.toLong).getOrElse(0)

            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {
                val response: Seq[EmployeeResponse] = employeeService.getAll(authResponse.id.toInt, offset, limit)
                Ok(Json.toJson(response))
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
                        val response: EmployeeResponse = employeeService.getById(authResponse.id.toInt, kioskId)
                        Ok(Json.toJson(response))
                    }
                    catch {
                        case e: RuntimeException => BadRequest(Json.obj("status" -> "Error", "message" -> e.getMessage))
                    }
                }
            }

    }


}

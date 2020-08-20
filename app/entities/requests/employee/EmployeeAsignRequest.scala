package entities.requests.employee


import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._


case class EmployeeAsignRequest(employee_id: Int, office_id: Int, station_id: Int)

object EmployeeAsignRequest {
  val form: Form[EmployeeAsignRequest] = Form(
    mapping(
      "employee_id" -> of[Int]
      , "office_id" -> of[Int]
      , "station_id" -> of[Int]
    )(EmployeeAsignRequest.apply)(EmployeeAsignRequest.unapply)
  )
}
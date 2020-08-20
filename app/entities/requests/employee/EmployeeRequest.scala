package entities.requests.employee

import play.api.data.Form
import play.api.data.Forms.{mapping, of}
import play.api.data.format.Formats._


case class EmployeeRequest(names: String, gender: String)

object EmployeeRequest {
  val form: Form[EmployeeRequest] = Form(
    mapping(
      "names" -> of[String]
      , "gender" -> of[String]

    )(EmployeeRequest.apply)(EmployeeRequest.unapply)
  )
}

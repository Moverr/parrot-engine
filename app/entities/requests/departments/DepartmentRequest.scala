package entities.requests.departments

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.data.format.Formats._



case class DepartmentRequest (office_id:Long,name:String,code:String)

object DepartmentRequest {
  val form: Form[DepartmentRequest] = Form(
    mapping(
      "office_id" -> of[Long]
      ,"name" -> of[String]
  ,"code" -> of[String]

    )(DepartmentRequest.apply)(DepartmentRequest.unapply)
  )
}

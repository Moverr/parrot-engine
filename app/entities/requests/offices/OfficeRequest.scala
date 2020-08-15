package entities.requests.offices


import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._


case class OfficeRequest(name:String,code:String,parent_office:Integer = null)

object OfficeRequest {
  val form: Form[OfficeRequest] = Form(
    mapping(
      "name" -> of[String]
      , "code" -> of[String]
      , "parent_office" -> of[Integer]
    )(OfficeRequest.apply)(OfficeRequest.unapply)
  )
}


package entities.requests.offices


import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._


case class OfficeAssignRequest(office_id: Int, station_id: Int)

object OfficeAssignRequest {
  val form: Form[OfficeAssignRequest] = Form(
    mapping(
      "office_id" -> of[Int]
      , "station_id" -> of[Int]
    )(OfficeAssignRequest.apply)(OfficeAssignRequest.unapply)
  )
}
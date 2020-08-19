package entities.requests.offices


import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._


case class OfficeAssignRequest(office_id: Long, station_id: Long)

object OfficeAssignRequest {
  val form: Form[OfficeAssignRequest] = Form(
    mapping(
      "office_id" -> of[Long]
      , "station_id" -> of[Long]
    )(OfficeAssignRequest.apply)(OfficeAssignRequest.unapply)
  )
}

package entities.requests.kiosks

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

case class KioskRequest(station_id:Long,   token:String, details:String)

object KioskRequest {
  val form: Form[KioskRequest] = Form(
    mapping(
      "station_id" -> of[Long]
      , "device_token" -> of[String]
      , "details" -> of[String]
    )(KioskRequest.apply)(KioskRequest.unapply)
  )
}



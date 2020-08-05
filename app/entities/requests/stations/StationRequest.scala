package entities.requests.stations

import play.api.data.Form
import play.api.data.Forms._


case class StationRequest(name: String, code: String)

object StationRequest {
  val form: Form[StationRequest] = Form(
    mapping(
      "name" -> nonEmptyText
      , "code" -> nonEmptyText
    )(StationRequest.apply)(StationRequest.unapply)
  )
}



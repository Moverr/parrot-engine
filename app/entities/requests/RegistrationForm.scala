package app.entities.requests

import play.api.data._
import play.api.data.Forms._


object RegistrationForm {
  val form: Form[RegistrationRequest] = Form(
    mapping(
      "email" -> text,
      "firstname" -> text,
      "lastname" -> text,
      "status" -> text

    )(RegistrationRequest.apply)(RegistrationRequest.unapply)
  )
}

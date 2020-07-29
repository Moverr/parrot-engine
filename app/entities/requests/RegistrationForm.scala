package app.entities.requests

import app.utils.StatusEnums
import play.api.data._
import play.api.data.Forms._


object RegistrationForm {
  val form: Form[RegistrationRequest] = Form(
    mapping(
      "email" -> text,
      "password" -> text
    )(RegistrationRequest.apply)(RegistrationRequest.unapply)
  )
}

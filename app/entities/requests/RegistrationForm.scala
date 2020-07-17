package app.entities.requests
import play.api.data._
import play.api.data.Forms._


object RegistrationForm {
  val form: Form[AuthenticationRequest] = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> text
    )(AuthenticationRequest.apply)(AuthenticationRequest.unapply)
  )
}

package app.entities.requests

import play.api.data._
import play.api.data.Forms._


object AuthForm {
  val form: Form[AuthenticationRequest] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> text
    )(AuthenticationRequest.apply)(AuthenticationRequest.unapply)
  )
}

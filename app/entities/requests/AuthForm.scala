package app.entities.requests

import play.api.data._
import play.api.data.Forms._


object AuthForm {
  val form: Form[AuthenticationRequest] = Form(
    mapping(
      "email" -> nonEmptyText,
      "age" -> number
    )(AuthenticationRequest.apply)(AuthenticationRequest.unapply)
  )
}

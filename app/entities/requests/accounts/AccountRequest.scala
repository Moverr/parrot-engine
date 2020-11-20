package entities.requests.accounts

import play.api.libs.json.Json

//todo: Organisation Name etc
case class AccountRequest(name:String)

/*
object AccountRequest {
  val form: Form[AccountRequest] = Form(
    mapping(
      "name" -> nonEmptyText
    )(AccountRequest.apply)(AccountRequest.unapply)
  )
}

*/
//todo: Better approach on converting the case class request 
object AccountRequest {
  implicit  val format = Json.format[AccountRequest]
}



package entities.requests.accounts

import play.api.data.Form
import play.api.data.Forms.{mapping, _}


//todo: Organisation Name etc
case class AccountReqquest(name:String)


object AccountReqquest {
  val form: Form[AccountReqquest] = Form(
    mapping(
      "name" -> nonEmptyText
    )(AccountReqquest.apply)(AccountReqquest.unapply)
  )
}



package controllers.v1

import entities.requests.accounts.AccountReqquest
import play.api.mvc._


object AccountController  extends Controller {


  def create = Action{
      implicit request =>
      val authRequest: AccountReqquest = AccountReqquest.form.bindFromRequest.get

    Ok("Create Account")
  }

  def get = Action{
    Ok("Get Account")
  }


/*
    Get List Create Update Archive Restore
    You should only be able to create only on  Account on a User.
    so. create and update.  the account name should be unique accross. Naa. not important.
 */

}
package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.accounts.AccountReqquest
import play.api.libs.json.Json
import play.api.mvc._
import services.AccountService


object AccountController extends Controller {

    def create = Action {
        implicit request =>

            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get
            print("......................................................")
            print(authorization)
            print("......................................................")

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if(authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
           else{
                println(s"*** headers: ${request.headers}")
                val accountRequest: AccountReqquest = AccountReqquest.form.bindFromRequest.get

                AccountService.create(authResponse.id,accountRequest)

                Ok("Create Account")

            }

    }

    def get = Action {
        Ok("Get Account")
    }


    /*
        Get List Create Update Archive Restore
        You should only be able to create only on  Account on a User.
        so. create and update.  the account name should be unique accross. Naa. not important.
     */

}
package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.accounts.AccountRequest
import entities.responses.accounts.AccountResponse
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import services.AccountService
import utils.HelperUtilities


object AccountController extends Controller {

    implicit  def userService =  UsersService.apply( HelperUtilities)


    def create = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {
s
               // val accountRequest: AccountRequest = AccountRequest.form.bindFromRequest.get
                val accountRequest: AccountRequest = AccountRequest.format[AccountRequest]

                AccountService.create(authResponse.id, accountRequest)
                Ok(HelperUtilities successResponse ("Record saved succesfully"))

            }

    }


    def get = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {


                implicit val resposnse = new Writes[AccountResponse] {
                    def writes(_account: AccountResponse) = Json.obj(
                        "name" -> _account.name
                    )
                }

                val response: AccountResponse = AccountService.get(authResponse.id)

                // Ok(Json.obj("name" -> response.name))
                Ok(Json.toJson(response))


            }
    }


    /*
        Get List Create Update Archive Restore
        You should only be able to create only on  Account on a User.
        so. create and update.  the account name should be unique accross. Naa. not important.
     */


}
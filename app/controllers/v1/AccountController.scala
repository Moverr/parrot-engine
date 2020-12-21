package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.accounts.AccountRequest
import entities.responses.accounts.AccountResponse
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import services.AccountService
import utils.HelperUtilities

object AccountController   extends Controller    {

    private implicit val ordersDatabase: Database = null
    private implicit val  dbConfigProvider: DatabaseConfigProvider = null


    implicit  def userService =  UsersService.apply( ordersDatabase, dbConfigProvider,  HelperUtilities)




    def create = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = userService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

               val accountRequest: AccountRequest = AccountRequest.form.bindFromRequest.get
                //val accountRequest: AccountRequest = AccountRequest.format[AccountRequest]

                AccountService.create(authResponse.id.toInt, accountRequest)
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
                val response: AccountResponse = AccountService.get(authResponse.id.toInt)

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


package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.accounts.AccountRequest
import entities.responses.accounts.AccountResponse
import javax.inject.Inject
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import services.AccountService
import utils.HelperUtilities

import app.entities.requests.{AuthenticationRequest, RegistrationRequest}
import app.entities.responses.AuthResponse
import controllers.v1.AuthController.BadRequest
import entities.responses.RegistrationResponse
import javax.inject.Inject
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import tables.{User, UserTable}
import utils.{HelperUtilities, PasswordHashing}

object AccountController extends Controller  {



    @Inject
    val userService:UsersService



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
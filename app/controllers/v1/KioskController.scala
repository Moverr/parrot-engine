package controllers.v1

import app.entities.responses.AuthResponse
import app.services.UsersService
import entities.requests.accounts.AccountRequest
import entities.responses.accounts.AccountResponse
import play.api.libs.json.{Json, Writes}
import play.api.mvc.{Action, Controller}
import services.AccountService
import utils.HelperUtilities

object KioskController extends Controller {

    implicit val resposnse = new Writes[AccountResponse] {
        def writes(_account: AccountResponse) = Json.obj(
            "name" -> _account.name
        )
    }


    def create = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {

                val accountRequest: AccountRequest = AccountRequest.form.bindFromRequest.get
                AccountService.create(authResponse.id, accountRequest)
                Ok(HelperUtilities successResponse ("Record saved succesfully"))

            }

    }


    def get = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            val authResponse: AuthResponse = UsersService.validateAuthorization(authorization)
            if (authResponse == null) BadRequest(Json.obj("status" -> "Un Authorized", "message" -> "Invalid Header String "))
            else {


                val response: AccountResponse = AccountService.get(authResponse.id)

                // Ok(Json.obj("name" -> response.name))
                Ok(Json.toJson(response))


            }
    }


    //todo: create

    //list

    //getby id

    //deactivate
    //activate
    //update
}

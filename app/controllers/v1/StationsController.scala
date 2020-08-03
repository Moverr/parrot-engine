package controllers.v1

import play.api.mvc.{Action, Controller}

//todo: deploy to the server and move
//todo: create stations and move on
object StationsController extends Controller {
    def create = Action {
        implicit request =>
            //todo: Authenticate
            val authorization = request.headers.get("Authorization").get

            Ok("Interesting")
    }

    def getAll = Action {
        Ok("List all Stations")
    }

    def get = Action {
        Ok("Get By Id")
    }

    def archive = Action {
        Ok("Archivee Station")
    }

    def refresh = Action {
        Ok("Refresh Station to Active")
    }

}

package controllers.v1

import play.api.mvc.{Action, Controller}

//todo: create stations and move on
class StationsController extends Controller {
    def create = Action {
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

package controllers.v1

import javax.inject.Inject
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._





object UsersController  extends Controller{
    def index = Action {
        Ok("Users  COntroller")
    }

}

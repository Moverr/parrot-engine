package controllers.v1

import app.dao.UserDAO
import javax.inject.Inject


import javax.inject.Inject
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._


object UsersController  extends Controller{
    def index = Action {
      //  var userDao =   UserDAO.getUser("moeoe","mowoee");
        Ok("Users  COntroller")
    }

}

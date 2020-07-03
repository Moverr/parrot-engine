package controllers.v1

import app.dao.UserDAO
import javax.inject.Inject


import javax.inject.Inject
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._
import play.api.db._
import play.api.cache.Cache
import play.api.Play.current



object UsersController  extends Controller{
    def index = Action {
        val conn = DB.getConnection()

        try{
            val stmt = conn.createStatement
            var str = "SELECT * FROM  \"default\".users ";

            print("STR: " + str)

            stmt.executeQuery(str)


        } finally {
            println("Interesting while closing");
            conn.close();
        }

      //  var userDao =   UserDAO.getUser("moeoe","mowoee");
        Ok("Users  COntroller")
    }

}

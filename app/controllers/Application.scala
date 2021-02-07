package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc._


@Singleton
 class Application @Inject()(cc: ControllerComponents)  extends AbstractController(cc) {
  def index = Action {
   val message:String = "PARROT VERSION 1.0.0"
    Ok(views.html.index(message))
  }
}


/*
object Application extends Controller {

  def index: Action[AnyContent] {
//    Ok(views.html.index(null))
   // Ok("Test")

  }

  def db = Action {
    var out = ""
    val conn = DB.getConnection()
    try {
      val stmt = conn.createStatement

      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())")

      val rs = stmt.executeQuery("SELECT tick FROM ticks")

      while (rs.next) {
        out += "Read from DB: " + rs.getTimestamp("tick") + "\n"
      }
    } finally {
      conn.close()
    }
    Ok(out)
  }
} */

package controllers.v1

import javax.inject.Inject
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._


object AccountController  extends Controller {
  def index = Action {
    Ok("Accounts COntroller")
  }


  def create = Action{
    ok("Create Account")
  }

  def get = Action{
    ok("Get Account")
  }

  def list = Action{
    ok("List Accounts")
  }



}
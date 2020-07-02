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
    Ok("Create Account")
  }

  def get = Action{
    Ok("Get Account")
  }

  def list = Action{
    Ok("List Accounts")
  }



}
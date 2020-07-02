package controllers.v1
import javax.inject.Inject
import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._


object DepartmentsController extends Controller  {
  def index = Action {
    Ok("Department COntroller")
  }


  def create = Action{
    Ok("Create Department")
  }

  def get = Action{
    Ok("Get Department")
  }

  def list = Action{
    Ok("List Department")
  }

}

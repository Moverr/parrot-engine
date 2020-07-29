package controllers.v1

import java.sql._
import java.util.Date

import app.entities.requests.{AuthForm, AuthenticationRequest, RegistrationForm, RegistrationRequest}
import play.api.libs.json.Json
import play.api.mvc._
import utils.PasswordHashing

//////
import app.utils.JwtUtility
import play.api.Play.current
import play.api.db._
import play.api.libs.json._
///////



object AuthController extends Controller {
    var conn = DB.getConnection()



    //todo: auth login
    def login() = Action {
        implicit request =>
            val authRequest: AuthenticationRequest = AuthForm.form.bindFromRequest.get

            implicit val roleResponserites = new Writes[AuthenticationRequest] {
                def writes(role: AuthenticationRequest) = Json.obj(
                    "username" -> role.username,
                    "password" -> role.password
                )
            }

            //todo: select from db where user name and password = xx s
            var query = "SELECT * FROM  \"default\".users as A " +
                    "WHERE " +
                    " A.username LIKE \'" + authRequest.username + "\' " +
                    "AND" +
                    " A.password LIKE \'" + authRequest.password + "\' ";

                val resultSet = fetchUserByEmailAndPassword(authRequest.username,  PasswordHashing.encryptPassword(authRequest.password));

                if (resultSet.next()) {
                    //todo: Populate a basic JWT Token


                        var username:String = null
                        var password:String = null
                        var createdOn:Date = null

                        while (resultSet.next()) {
                            username = resultSet.getString("username")
                            password = resultSet.getString("password")
                            createdOn = resultSet.getDate("created_on")
                        }

                    //todo: make an implementation for the Auth Response
                    val  token =  JwtUtility createToken(username+":"+password)
                    Ok(token)

                }else{
                      Unauthorized(Json.obj("status" -> "Un Authorized", "message" -> " Bingo  User is not Authorized"))
                }




    }


//todo: Register

    def register() = Action {

        implicit request =>
            val registrationRequest: RegistrationRequest = RegistrationForm.form.bindFromRequest.get

            //todo: check for nulls
            if (registrationRequest.email.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Email is Mandatory"))
            }
            else if (registrationRequest.password.isEmpty()) {
                BadRequest(Json.obj("status" -> "Error", "message" -> "Password is Mandatory"))
            } else {
               var userExists:Boolean =  validateUser(registrationRequest.email,registrationRequest.password)
               if(userExists == true)
                BadRequest(Json.obj("code" -> 400,"status" -> "Badrequest", "message" -> "User already registered to the system " ))
                else
                   {
                       var query = "INSERT INTO  \"default\".users (username,password)  values ('"+registrationRequest.email+"','"+PasswordHashing.encryptPassword(registrationRequest.password)+"') " ;
                       conn = DB.getConnection()
                       val stmt = conn.createStatement
                       var result = stmt.execute(query)
                       //todo: create a user and move on
                       Ok(Json.obj("code" -> 200,"status" -> "Success", "message" -> "User Created" ))
                   }
            }

    }


    def validateUser(email:String,password:String): Boolean ={

        var query = "SELECT * FROM  \"default\".users as A " +
                "WHERE " +
                " A.username LIKE \'" + email + "\' " ;


        conn = DB.getConnection()

        val stmt = conn.createStatement

        print("STR: " + query)

        var resultSet = stmt.executeQuery(query)


        if(resultSet.next()){

            true

        }else{
           false
        }




    }

    def fetchUserByEmailAndPassword(email: String, password: String): ResultSet = {

        var query = "SELECT * FROM  \"default\".users as A " +
                "WHERE " +
                " A.username LIKE \'" + email + "\' " +
                "AND" +
                " A.password LIKE \'" + password + "\' ";
        print("STR: " + query)
        conn = DB.getConnection()
        val stmt = conn.createStatement
        var resultSet = stmt.executeQuery(query)
        resultSet


    }

    def ExecuteQuerySelect(query: String): Any = {


        conn = DB.getConnection()

        val stmt = conn.createStatement

        print("STR: " + query)

        val resultSet = stmt.executeQuery(query)
        resultSet
    }
    

}

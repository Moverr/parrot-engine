package app.services

import java.sql.ResultSet

import controllers.v1.AuthController.conn



//////
import play.api.Play.current
import play.api.db._
///////





class UsersService extends BaseServiceTrait {

  def createUser1(username:String,password:String): Boolean ={

   false
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


  override def createUser(): Unit ={

  }

  override def list(offset:Int,limit:Int): Unit ={

  }

  override def get(id:Int): Unit ={

  }
  override def search(query:String,offset:Int,limit:Int): Unit ={

  }

  override def populateResponse(): Unit ={

  }

  override def populateEntity(): Unit ={

  }


}
object UsersService extends UsersService
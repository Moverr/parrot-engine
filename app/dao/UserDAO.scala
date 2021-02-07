package app.dao

import app.dto.User


object UserDAO {

  //todo: Get Usesr by username and email
  def getUser(username: String, password: String): List[User] = {

    /*
    return JPA.withTransaction(new play.libs.F.Function0[List[User]]() {
      @Override
      def apply():List[User] = {
        return JPA.em().createQuery("from users WHERE username = :username AND password = :password").
          setParameter("username", username).setParameter("password", password).
          getResultList().asInstanceOf[List[User]]

      }
    })*/
    null
  }
}

//todo: Case Class
case class UserDAO(username:String, password:String) {
  def checkPassword(password: String): Boolean = this.password == password
}

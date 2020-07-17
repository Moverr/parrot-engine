package app.entities.requests

/*
This case class should help in the Authentication using username and password
 */
case class AuthenticationRequest(username: String, password: String)

//object AuthenticationRequest{
//  def apply(email: String, age: Int): AuthenticationRequest = new AuthenticationRequest(email, age)
//  def unapply(arg: AuthenticationRequest): Option[(String, Int)] = ???
//
//}
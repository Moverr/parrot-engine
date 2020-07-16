package app.entities.requests

case class AuthenticationRequest(email: String, age: Int)

//object AuthenticationRequest{
//  def apply(email: String, age: Int): AuthenticationRequest = new AuthenticationRequest(email, age)
//  def unapply(arg: AuthenticationRequest): Option[(String, Int)] = ???
//
//}
package services.traits

import app.entities.requests.{AuthenticationRequest, RegistrationRequest}
import app.entities.responses.AuthResponse
import entities.responses.RegistrationResponse
import tables.User

import scala.concurrent.Future

trait TUserService {

  def register(registrationRequest: RegistrationRequest): Boolean

  //todo: Login User by Username and Password
  def login(authRequest: AuthenticationRequest): AuthResponse

  val users

  //todo: Get User by Username and Email
  def fetchUserByEmailAndPassword(email: String, password: String): Future[User]

  def ValidateIfUserExists(email: String, password: String): Boolean

  def createUser(registrationRequest: RegistrationRequest): RegistrationResponse

  //todo: validate token and return a User Object
  def validateAuthorization(authentication: String): AuthResponse

  def populateResponse(_user: User)
}

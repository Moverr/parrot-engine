package app.services

import app.entities.requests.RegistrationRequest
import entities.responses.RegistrationResponse

trait UserServiceTrait {

  def createUser(registrationRequest:RegistrationRequest): RegistrationResponse

  def list(offset: Int, limit: Int): Unit

  def get(id: Int): Unit

  def search(query: String, offset: Int, limit: Int): Unit

  def populateResponse(): Unit

  def populateEntity(): Unit
}

package app.services

import app.entities.requests.RegistrationRequest

trait UserServiceTrait {

  def createUser(registrationRequest:RegistrationRequest): Any

  def list(offset: Int, limit: Int): Unit

  def get(id: Int): Unit

  def search(query: String, offset: Int, limit: Int): Unit

  def populateResponse(): Unit

  def populateEntity(): Unit
}

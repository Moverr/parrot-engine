package app.services

trait UserServiceTrait {

  def createUser(): Any

  def list(offset: Int, limit: Int): Unit

  def get(id: Int): Unit

  def search(query: String, offset: Int, limit: Int): Unit

  def populateResponse(): Unit

  def populateEntity(): Unit
}

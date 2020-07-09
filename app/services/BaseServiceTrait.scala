package app.services

trait BaseServiceTraits {

  def createUser(): Any

  def list(offset: Int, limit: Int): Any

  def get(id: Int): Unit

  def search(query: String, offset: Int, limit: Int): Any

  def populateResponse(): Any

  def populateEntity(): Any
}

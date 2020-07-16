package app.services

trait BaseServiceTrait {

  def createUser(): Any

  def list(offset: Int, limit: Int): Unit

  def get(id: Int): Unit

  def search(query: String, offset: Int, limit: Int): Unit

  def populateResponse(): Unit

  def populateEntity(): Unit
}
rdcli -h services.newvisionapp.com  -p 6379


redis-cli -h services.newvisionapp.com -p 6379

package tables

//import scala.concurrent._
//import scala.concurrent.duration._
//import scala.concurrent.ExecutionContext.Implicits.global
//import slick.driver.PostgresDriver.api._

import slick.driver.PostgresDriver.api._
//import slick.driver.pos.
object Main {

  case class  Account(
                       owner:Long,
                       name:String,
                       external_id:String,
                       author_id:Long=0L
                     )
  class  AccountTable(tag: Tag) extends Table[Account](tag,"accounts"){
    def owner = column[Long]("owner")
    def name  = column[String]("name")
    def external_id  = column[String]("external_id")
    def author_id     = column[Long]("author_id")


    def * = (owner, name, external_id,author_id) <> (Account.tupled, Account.unapply)
  }
}

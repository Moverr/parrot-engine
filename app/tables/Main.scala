package tables

//import scala.concurrent._
//import scala.concurrent.duration._
//import scala.concurrent.ExecutionContext.Implicits.global
//import slick.driver.PostgresDriver.api._

import java.sql.Date
import java.util.Formatter


import slick.driver.PostgresDriver.api._

import java.sql.Timestamp
import org.joda.time.DateTime
import org.joda.time.DateTimeZone.UTC



//import slick.driver.pos.
object Main {

  case class Account(
                      id: Long = 0L,
                      owner: Long,
                      name: String,
                      created_on: DateTime,
                      updated_on: DateTime,
                      author_id: Long,
                      updated_by: Long,
                      external_id: String

                    )

  class AccountTable(tag: Tag) extends Table[Account](tag, "account") {

   def id  = column[Long]("id")
    def owner= column[Long]("id")
    def name= column[String]("name")
    def created_on= column[DateTime]("created_on")
    def updated_on= column[DateTime]("updated_on")
    def author_id= column[Long]("author_id")
    def updated_by= column[Long]("updated_by")
    def external_id= column[String]("external_id")


    def * = (owner, name, created_on, updated_on,author_id,updated_by,external_id) <> (Account.tupled, Account.unapply)
  }

}

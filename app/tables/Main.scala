package tables

//import scala.concurrent._
//import scala.concurrent.duration._
//import scala.concurrent.ExecutionContext.Implicits.global
//import slick.driver.PostgresDriver.api._

import play.data.format.Formats.DateTime
import slick.driver.PostgresDriver.api._

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
    def owner = column[Long]("owner")

    def name = column[String]("name")

    def external_id = column[String]("external_id")

    def author_id = column[Long]("author_id")


    def * = (owner, name, external_id, author_id) <> (Account.tupled, Account.unapply)
  }

}

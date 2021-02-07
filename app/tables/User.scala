package tables

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._

//todo:   user    table
case class User(id: Long = 0L, username: String, password: String, author_id: Long, created_on: DateTime, updated_by: Long, updated_on: Option[DateTime])

class UserTable(tag: Tag) extends Table[User](tag, "user") {
  override def * = (id, username, password, author_id, created_on, updated_by, updated_on.?) <> (User.tupled, User.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def username = column[String]("username")

  def password = column[String]("password")

  def created_on = column[DateTime]("created_on")

  def updated_on = column[DateTime]("dateupdated")

  def author_id = column[Long]("author_id")

  def updated_by = column[Long]("updated_by")
}

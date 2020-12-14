package tables

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._
//todo: Profiles    table
case class Profile(
                    id: Long = 0L,
                    user_id: Long,
                    firstname: String,
                    lastname: String,
                    author_id: Long,
                    created_on: DateTime,
                    updated_by: Long,
                    updated_on: Option[DateTime]
                  )

class ProfileTable(tag: Tag) extends Table[Profile](tag, "profile") {
  override def * = (id, user_id, firstname, lastname, author_id, created_on, updated_by, updated_on.?) <> (Profile.tupled, Profile.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def user_id = column[Long]("user_id")

  def firstname = column[String]("firstname")

  def lastname = column[String]("lastname")

  def created_on = column[DateTime]("created_on")

  def updated_on = column[DateTime]("dateupdated")

  def author_id = column[Long]("author_id")

  def updated_by = column[Long]("updated_by")
}
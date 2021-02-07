package tables

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._

//todo:   user   Roles  table
case class UserRole(
                     id: Long = 0L,
                     user_id: Long,
                     role_id: Long,
                     status: String,

                     author_id: Long,
                     created_on: DateTime,
                     updated_by: Long,
                     updated_on: Option[DateTime]
                   )

class UserRoleTable(tag: Tag) extends Table[UserRole](tag, "user_role") {
  override def * = (id, user_id, role_id, status, author_id, created_on, updated_by, updated_on.?) <> (UserRole.tupled, UserRole.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def user_id = column[Long]("user_id")

  def role_id = column[Long]("role_id")

  def status = column[String]("status")

  def created_on = column[DateTime]("created_on")

  def updated_on = column[DateTime]("dateupdated")

  def author_id = column[Long]("author_id")

  def updated_by = column[Long]("updated_by")
}

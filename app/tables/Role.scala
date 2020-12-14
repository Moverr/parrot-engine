package tables

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._
//todo: Roles    table
case class Role(
                 id: Long = 0L,
                 code: String,
                 name: String,
                 is_system: Boolean,
                 author_id: Long,
                 created_on: DateTime,
                 updated_by: Long,
                 updated_on: Option[DateTime]
               )

class RoleTable(tag: Tag) extends Table[Role](tag, "role") {
  override def * = (id, code, name, is_system, author_id, created_on, updated_by, updated_on.?) <> (Role.tupled, Role.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def code = column[String]("code")

  def name = column[String]("name")

  def is_system = column[Boolean]("is_system")

  def created_on = column[DateTime]("created_on")

  def updated_on = column[DateTime]("dateupdated")

  def author_id = column[Long]("author_id")

  def updated_by = column[Long]("updated_by")
}
package tables

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._
//todo: Permission    table
case class Permission(
                       id: Long = 0L,
                       code: String,
                       name: String,
                       grouping: String
                     )

class PermissionTable(tag: Tag) extends Table[Permission](tag, "permissions") {
  override def * = (id, code, name, grouping) <> (Permission.tupled, Permission.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def code = column[String]("code")

  def name = column[String]("name")

  def grouping = column[String]("grouping")
}
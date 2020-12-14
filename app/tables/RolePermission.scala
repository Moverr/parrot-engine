package tables

import org.joda.time.DateTime
import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._
//todo: Roles  Permissions    table
case class RolePermission(
                           id: Long = 0L,
                           role_id: Long,
                           permission_id: Long,

                           author_id: Long,
                           created_on: DateTime,
                           updated_by: Long,
                           updated_on: Option[DateTime]
                         )

class RolePermissionTable(tag: Tag) extends Table[RolePermission](tag, "role_permission") {
  override def * = (id, role_id, permission_id, author_id, created_on, updated_by, updated_on.?) <> (RolePermission.tupled, RolePermission.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def role_id = column[Long]("role_id")

  def permission_id = column[Long]("station_id")

  def created_on = column[DateTime]("created_on")

  def updated_on = column[DateTime]("dateupdated")

  def author_id = column[Long]("author_id")

  def updated_by = column[Long]("updated_by")
}
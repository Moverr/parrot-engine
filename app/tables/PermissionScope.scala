package tables


import slick.jdbc.PostgresProfile.api._
//import defaults ::

//todo: Permission Scope   table
case class PermissionScope(
                            id: Long = 0L,
                            key: String,
                            value: String
                          )

class PermissionScopeTable(tag: Tag) extends Table[PermissionScope](tag, "permission_scope") {
  override def * = (id, key, value) <> (PermissionScope.tupled, PermissionScope.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def key = column[String]("key")

  def value = column[String]("value")
}

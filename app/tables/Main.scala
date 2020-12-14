package tables


import java.sql.Date

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery
//import defaults ::
import implicits.CustomColumnTypes._


object Main {

  //Query DSL to intergrate the Table
  lazy val AccountTable = TableQuery[AccountTable]
  lazy val DepartmentTable = TableQuery[DepartmentTable]
  lazy val EmployeeTable = TableQuery[EmployeeTable]
  lazy val EmployeeStationTable = TableQuery[EmployeeStationTable]
  lazy val KioskTable = TableQuery[KioskTable]
  lazy val OfficeTable = TableQuery[OfficeTable]
  lazy val OfficeStationTable = TableQuery[OfficeStationTable]
  lazy val PermissionScopeTable = TableQuery[PermissionScopeTable]
  lazy val PermissionTable = TableQuery[PermissionTable]
  lazy val ProfileTable = TableQuery[ProfileTable]
  lazy val RoleTable = TableQuery[RoleTable]
  lazy val role_permissions = TableQuery[RolePermissionTable]
  lazy val stations = TableQuery[StationTable]
  lazy val user_roles = TableQuery[UserRoleTable]


  // Create an in-memory H2 database; 
  //val db: Database = Database.forConfig("DefaultDS")

  val connections: Option[Int] = Some(10)
  //todo: COnfiguring a database
  val databaseConnector: Database = Database.forConfig("enginedb")

















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


  //todo: Stations    table
  case class Station(
                      id: Long = 0L,
                      account_id: Long,
                      name: String,
                      code: String
                    )

  class StationTable(tag: Tag) extends Table[Station](tag, "stations") {
    override def * = (id, account_id, name, code) <> (Station.tupled, Station.unapply)

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def account_id = column[Long]("account_id")

    def name = column[String]("name")

    def code = column[String]("code")

  }


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


  lazy val users = TableQuery[UserTable]

  //todo: Query
  val query = users.map(p=>(p.id, p.username,p.password, p.author_id, p.created_on, p.updated_by, p.updated_on))
  val action = databaseConnector.run(query.result)

}

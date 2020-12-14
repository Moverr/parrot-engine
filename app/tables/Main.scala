package tables


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

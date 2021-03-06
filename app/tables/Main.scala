package tables


import slick.lifted.TableQuery
//import defaults ::

//////
///////


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
  //val db: Database = Database.forConfig("mydb")

  val connections: Option[Int] = Some(10)

  //todo: COnfigur/ing a database
 //val databaseConnector =    getConnection()
/*
  lazy val users = TableQuery[UserTable]

  //todo: Query
  val query = users.map(p=>(p.id, p.username,p.password, p.author_id, p.created_on, p.updated_by, p.updated_on))
  val action = databaseConnector.run(query.result)
  */

}

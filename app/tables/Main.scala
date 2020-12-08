package tables


import java.sql.Date

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._


object Main {

  //Query DSL to intergrate the Table
  lazy  val AccountTable = TableQuery[AccountTable]
  lazy  val DepartmentTable = TableQuery[DepartmentTable]
  lazy  val EmployeeTable = TableQuery[EmployeeTable]
  lazy  val EmployeeStationTable = TableQuery[EmployeeStationTable]
  lazy  val KioskTable = TableQuery[KioskTable]
  lazy  val OfficeTable = TableQuery[OfficeTable]
  lazy  val OfficeStationTable = TableQuery[OfficeStationTable]
  lazy  val PermissionScopeTable = TableQuery[PermissionScopeTable]
  lazy  val PermissionTable = TableQuery[PermissionTable]
  lazy  val ProfileTable = TableQuery[ProfileTable]
  lazy  val RoleTable = TableQuery[RoleTable]
  lazy  val RolePermissionTable = TableQuery[RolePermissionTable]
  lazy  val StationTable = TableQuery[StationTable]
  lazy  val UserRoleTable = TableQuery[UserRoleTable]
  lazy  val UserTable = TableQuery[UserTable]


  // Create an in-memory H2 database; 
  //val db: Database = Database.forConfig("DefaultDS")

  val connections:Option[Int] =  Some(10)
//todo: COnfiguring a database
  val db:Database = Database.forName("DefaultDS",connections,null)

  //todo: Define Scehma
  //todo: Accounts Table
  case class Account(
                      id: Long = 0L,
                      owner: Long,
                      name: String,
                      created_on: DateTime,
                      updated_on:Option[DateTime],
                      author_id: Long,
                      updated_by: Long,
                      external_id: String
                    )

  //todo: Define Table
  class AccountTable(tag: Tag) extends Table[Account](tag, "account") {

   def * = (id, owner, name, created_on, updated_on.?, author_id, updated_by, external_id) <> (Account.tupled, Account.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def owner = column[Long]("id")

    def name = column[String]("name")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("updated_on")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")

    def external_id = column[String]("external_id")

   // def * = (id, owner, name, created_on, updated_on.?, author_id, updated_by, external_id).mapTo[Account]

  }

  //todo: Departments table
  case class Department(
                      id: Long = 0L,
                      name: String,
                      code: String,
                      office: Long,
                      parent:Long
                    )

  class DepartmentTable(tag:Tag) extends Table[Department](tag,"departments"){
    override def * = (id,name,code,office,parent) <> (Department.tupled,Department.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def name = column[String]("name")

    def code  = column[String]("code")

    def office = column[Long]("office_id")

    def parent = column[Long]("parent_department")
  }

  //todo: Employee  table
  case class Employee(
                         id: Long = 0L,
                         name: String,
                         gender: String,
                         created_on: DateTime,
                         updated_on:Option[DateTime],
                         author_id: Long,
                         updated_by: Long
                       )

  class EmployeeTable(tag:Tag) extends Table[Employee](tag,"employees"){
    override def * = (id,name,gender,created_on,updated_on.?,author_id,updated_by) <> (Employee.tupled,Employee.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def name = column[String]("names")

    def gender  = column[String]("gender")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("updated_on")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")
  }

  //todo: Employee Station   table
  case class EmployeeStation(
                       id: Long = 0L,
                       employee_id: Long,
                       office_id: Long,
                       station_id: Long
                     )

  class EmployeeStationTable(tag:Tag) extends Table[EmployeeStation](tag,"employee_station"){
    override def * = (id,employee_id,office_id,station_id) <> (EmployeeStation.tupled,EmployeeStation.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def employee_id = column[Long]("employee_id")

    def office_id = column[Long]("office_id")

    def station_id = column[Long]("station_id")
  }

  //todo: Kiosk  table
  case class Kiosk(
                    id: Long = 0L,
                    reference: String,
                    details: String,
                    token: String,
                    station: Long,
                    created_on: DateTime,
                    updated_on:Option[DateTime],
                    author_id: Long,
                    updated_by:Long

                  )

  class KioskTable(tag:Tag) extends Table[Kiosk](tag,"kiosks") {
    override def * = (id,reference,details,token,station,created_on,updated_on.?,author_id,updated_by) <> (Kiosk.tupled,Kiosk.unapply)

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def reference = column[String]("reference")

    def details = column[String]("details")

    def token = column[String]("device_token")

    def station = column[Long]("station_id")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("updated_on")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")

  }

//todo: Offices
  case class Office(
                     id: Long = 0L,
                     name: String,
                     date_opened: Date,
                     parent_office: Option[Long],
                     created_on: DateTime,
                     updated_on:Option[DateTime],
                     author_id: Long,
                     updated_by:Option[Long],
                     account_id:Long

                  )

  class OfficeTable(tag:Tag) extends Table[Office](tag,"offices") {
    override def * = (id,name,date_opened,parent_office.?,created_on,updated_on.?,author_id,updated_by.?,account_id) <> (Office.tupled,Office.unapply)

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def date_opened = column[Date]("date_opened")

    def parent_office = column[Long]("parent_office")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("updated_on")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")

    def account_id = column[Long]("account_id")

  }

  //todo: Office Station   table
  case class OfficeStation(
                              id: Long = 0L,
                              office_id: Long,
                              station_id: Long
                            )

  class OfficeStationTable(tag:Tag) extends Table[OfficeStation](tag,"office_station"){
    override def * = (id,office_id,station_id) <> (OfficeStation.tupled,OfficeStation.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def office_id = column[Long]("office_id")

    def station_id = column[Long]("station_id")
  }

  //todo: Permission Scope   table
  case class PermissionScope(
                            id: Long = 0L,
                            key: String,
                            value: String
                          )

  class PermissionScopeTable(tag:Tag) extends Table[PermissionScope](tag,"permission_scope"){
    override def * = (id,key,value) <> (PermissionScope.tupled,PermissionScope.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def key = column[String]("key")

    def value = column[String]("value")
  }

  //todo: Permission    table
  case class Permission(
                              id: Long = 0L,
                              code: String,
                              name: String,
                              grouping:String
                            )

  class PermissionTable(tag:Tag) extends Table[Permission](tag,"permissions"){
    override def * = (id,code,name,grouping) <> (Permission.tupled,Permission.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def code = column[String]("code")

    def name = column[String]("name")

    def grouping = column[String]("grouping")
  }

  //todo: Profiles    table
  case class Profile(
                         id: Long = 0L,
                         user_id:Long,
                         firstname: String,
                         lastname: String,
                         author_id:Long,
                         created_on: DateTime,
                         updated_by: Long,
                         updated_on:Option[DateTime]
                       )

  class ProfileTable(tag:Tag) extends Table[Profile](tag,"profile"){
    override def * = (id,user_id,firstname,lastname,author_id,created_on,updated_by,updated_on.?) <> (Profile.tupled,Profile.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def user_id = column[Long]("user_id")

    def firstname = column[String]("firstname")

    def lastname = column[String]("lastname")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("dateupdated")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")
  }

  //todo: Roles    table
  case class Role(
                      id: Long = 0L,
                      code:String,
                      name: String,
                      is_system: Boolean,

                      author_id:Long,
                      created_on: DateTime,
                      updated_by: Long,
                      updated_on:Option[DateTime]
                    )

  class RoleTable(tag:Tag) extends Table[Role](tag,"role"){
    override def * = (id,code,name,is_system,author_id,created_on,updated_by,updated_on.?) <> (Role.tupled,Role.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def code = column[String]("code")

    def name = column[String]("name")

    def is_system = column[Boolean]("is_system")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("dateupdated")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")
  }

  //todo: Roles  Permissions    table
  case class RolePermission(
                            id: Long = 0L,
                            role_id: Long,
                            permission_id: Long,

                              author_id:Long,
                              created_on: DateTime,
                              updated_by: Long,
                              updated_on:Option[DateTime]
                          )

  class RolePermissionTable(tag:Tag) extends Table[RolePermission](tag,"role_permission"){
    override def * = (id,role_id,permission_id,author_id,created_on,updated_by,updated_on.?) <> (RolePermission.tupled,RolePermission.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

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
                   account_id:Long,
                   name: String,
                   code:String
                 )

  class StationTable(tag:Tag) extends Table[Station](tag,"stations"){
    override def * = (id,account_id,name,code) <> (Station.tupled,Station.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
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

                             author_id:Long,
                             created_on: DateTime,
                             updated_by: Long,
                             updated_on:Option[DateTime]
                           )

  class UserRoleTable(tag:Tag) extends Table[UserRole](tag,"user_role"){
    override def * = (id,user_id,role_id,status,author_id,created_on,updated_by,updated_on.?) <> (UserRole.tupled,UserRole.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def user_id = column[Long]("user_id")

    def role_id = column[Long]("role_id")
    def status = column[String]("status")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("dateupdated")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")
  }





  //todo:   user    table
  case class User(
                       id: Long = 0L,
                       username: String,
                       password: String,

                       author_id:Long,
                       created_on: DateTime,
                       updated_by: Long,
                       updated_on:Option[DateTime]
                     )

  class UserTable(tag:Tag) extends Table[User](tag,"user"){
    override def * = (id,username,password,author_id,created_on,updated_by,updated_on.?) <> (User.tupled,User.unapply)

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)

    def username = column[String]("username")

    def password = column[String]("password")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("dateupdated")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")
  }





}

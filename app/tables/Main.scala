package tables


import java.sql.Date

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._


object Main {

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

  class AccountTable(tag: Tag) extends Table[Account](tag, "account") {

    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def owner = column[Long]("id")
    def name = column[String]("name")
    def created_on = column[DateTime]("created_on")
    def updated_on = column[DateTime]("updated_on")
    def author_id = column[Long]("author_id")
    def updated_by = column[Long]("updated_by")
    def external_id = column[String]("external_id")


   def * = (id, owner, name, created_on, updated_on.?, author_id, updated_by, external_id) <> (Account.tupled, Account.unapply)

   // def * = (id, owner, name, created_on, updated_on.?, author_id, updated_by, external_id).mapTo[Account]

  }

  //Query DSL to intergrate the Table
  lazy  val AccountTable = TableQuery[AccountTable]

  //todo: Departments table
  case class Department(
                      id: Long = 0L,
                      name: String,
                      code: String,
                      office: Long,
                      parent:Long
                    )

  class DepartmentTable(tag:Tag) extends Table[Department](tag,"departments"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    def code  = column[String]("code")
    def office = column[Long]("office_id")
    def parent = column[Long]("parent_department")

    override def * = (id,name,code,office,parent) <> (Department.tupled,Department.unapply)
  }

  lazy  val DepartmentTable = TableQuery[DepartmentTable]




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
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def name = column[String]("names")
    def gender  = column[String]("gender")

    def created_on = column[DateTime]("created_on")
    def updated_on = column[DateTime]("updated_on")
    def author_id = column[Long]("author_id")
    def updated_by = column[Long]("updated_by")

    override def * = (id,name,gender,created_on,updated_on.?,author_id,updated_by) <> (Employee.tupled,Employee.unapply)
  }

  lazy  val EmployeeTable = TableQuery[EmployeeTable]


  //todo: Employee Station   table
  case class EmployeeStation(
                       id: Long = 0L,
                       employee_id: Long,
                       office_id: Long,
                       station_id: Long
                     )

  class EmployeeStationTable(tag:Tag) extends Table[EmployeeStation](tag,"employee_station"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def employee_id = column[Long]("employee_id")
    def office_id = column[Long]("office_id")
    def station_id = column[Long]("station_id")

    override def * = (id,employee_id,office_id,station_id) <> (EmployeeStation.tupled,EmployeeStation.unapply)
  }

  lazy  val EmployeeStationTable = TableQuery[EmployeeStationTable]

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
                    updated_by:Option[Long]

                  )


  class KioskTable(tag:Tag) extends Table[Kiosk](tag,"kiosks") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def reference = column[String]("reference")

    def details = column[String]("details")

    def token = column[String]("device_token")
    def station = column[Long]("station_id")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("updated_on")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")



    override def * = (id,reference,details,token,station,created_on,updated_on.?,author_id,updated_by.?) <> (Kiosk.tupled,Kiosk.unapply)

  }
  lazy  val KioskTable = TableQuery[KioskTable]

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
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def date_opened = column[Date]("date_opened")

    def parent_office = column[Long]("parent_office")

    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("updated_on")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")
    def account_id = column[Long]("account_id")



    override def * = (id,name,date_opened,parent_office.?,created_on,updated_on.?,author_id,updated_by.?,account_id) <> (Office.tupled,Office.unapply)

  }
  lazy  val OfficeTable = TableQuery[OfficeTable]




  //todo: Office Station   table
  case class OfficeStation(
                              id: Long = 0L,
                              office_id: Long,
                              station_id: Long
                            )

  class OfficeStationTable(tag:Tag) extends Table[OfficeStation](tag,"office_station"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def office_id = column[Long]("office_id")
    def station_id = column[Long]("station_id")

    override def * = (id,office_id,station_id) <> (OfficeStation.tupled,OfficeStation.unapply)
  }

  lazy  val OfficeStationTable = TableQuery[OfficeStationTable]



  //todo: Permission Scope   table
  case class PermissionScope(
                            id: Long = 0L,
                            key: String,
                            value: String
                          )

  class PermissionScopeTable(tag:Tag) extends Table[PermissionScope](tag,"permission_scope"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def key = column[String]("key")
    def value = column[String]("value")

    override def * = (id,key,value) <> (PermissionScope.tupled,PermissionScope.unapply)
  }

  lazy  val PermissionScopeTable = TableQuery[PermissionScopeTable]



  //todo: Permission    table
  case class Permission(
                              id: Long = 0L,
                              code: String,
                              name: String,
                              grouping:String
                            )

  class PermissionTable(tag:Tag) extends Table[Permission](tag,"permissions"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def code = column[String]("code")
    def name = column[String]("name")
    def grouping = column[String]("grouping")

    override def * = (id,code,name,grouping) <> (Permission.tupled,Permission.unapply)
  }

  lazy  val PermissionTable = TableQuery[PermissionTable]



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
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def user_id = column[Long]("user_id")
    def firstname = column[String]("firstname")
    def lastname = column[String]("lastname")




    def created_on = column[DateTime]("created_on")

    def updated_on = column[DateTime]("dateupdated")

    def author_id = column[Long]("author_id")

    def updated_by = column[Long]("updated_by")



    override def * = (id,user_id,firstname,lastname,author_id,created_on,updated_by,updated_on.?) <> (Profile.tupled,Profile.unapply)
  }

  lazy  val ProfileTable = TableQuery[ProfileTable]






}

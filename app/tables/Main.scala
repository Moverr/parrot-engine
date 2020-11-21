package tables


import org.joda.time.DateTime

import  slick.jdbc.PostgresProfile.api._
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

    override def * = (id,name,code,office,parent).mapTo[Department]
  }

  lazy  val DepartmentTable = TableQuery[DepartmentTable]

}

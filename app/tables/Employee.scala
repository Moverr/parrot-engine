package tables

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._

//todo: Employee  table
case class Employee(
                     id: Long = 0L,
                     name: String,
                     gender: String,
                     created_on: DateTime,
                     updated_on: Option[DateTime],
                     author_id: Long,
                     updated_by: Long
                   )

class EmployeeTable(tag: Tag) extends Table[Employee](tag, "employees") {
  override def * = (id, name, gender, created_on, updated_on.?, author_id, updated_by) <> (Employee.tupled, Employee.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("names")

  def gender = column[String]("gender")

  def created_on = column[DateTime]("created_on")

  def updated_on = column[DateTime]("updated_on")

  def author_id = column[Long]("author_id")

  def updated_by = column[Long]("updated_by")
}


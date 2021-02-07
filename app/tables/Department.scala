package tables
import slick.jdbc.PostgresProfile.api._
//import defaults ::


//todo: Departments table
case class Department(
                       id: Long = 0L,
                       name: String,
                       code: String,
                       office: Long,
                       parent: Long
                     )

class DepartmentTable(tag: Tag) extends Table[Department](tag, "departments") {
  override def * = (id, name, code, office, parent) <> (Department.tupled, Department.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def code = column[String]("code")

  def office = column[Long]("office_id")

  def parent = column[Long]("parent_department")
}
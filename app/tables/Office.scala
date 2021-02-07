package tables

import java.sql.Date

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._

//todo: Offices
case class Office(
                   id: Long = 0L,
                   name: String,
                   date_opened: Date,
                   parent_office: Option[Long],
                   created_on: DateTime,
                   updated_on: Option[DateTime],
                   author_id: Long,
                   updated_by: Option[Long],
                   account_id: Long

                 )

class OfficeTable(tag: Tag) extends Table[Office](tag, "offices") {
  override def * = (id, name, date_opened, parent_office.?, created_on, updated_on.?, author_id, updated_by.?, account_id) <> (Office.tupled, Office.unapply)

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
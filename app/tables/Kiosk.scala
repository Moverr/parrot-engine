package tables

import org.joda.time.DateTime
import slick.jdbc.PostgresProfile.api._
//import defaults ::
import implicits.CustomColumnTypes._

//todo: Kiosk  table
case class Kiosk(
                  id: Long = 0L,
                  reference: String,
                  details: String,
                  token: String,
                  station: Long,
                  created_on: DateTime,
                  updated_on: Option[DateTime],
                  author_id: Long,
                  updated_by: Long

                )

class KioskTable(tag: Tag) extends Table[Kiosk](tag, "kiosks") {
  override def * = (id, reference, details, token, station, created_on, updated_on.?, author_id, updated_by) <> (Kiosk.tupled, Kiosk.unapply)

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

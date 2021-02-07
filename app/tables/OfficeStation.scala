package tables

import slick.jdbc.PostgresProfile.api._
//import defaults ::

//todo: Office Station   table
case class OfficeStation(
                          id: Long = 0L,
                          office_id: Long,
                          station_id: Long
                        )

class OfficeStationTable(tag: Tag) extends Table[OfficeStation](tag, "office_station") {
  override def * = (id, office_id, station_id) <> (OfficeStation.tupled, OfficeStation.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def office_id = column[Long]("office_id")

  def station_id = column[Long]("station_id")
}

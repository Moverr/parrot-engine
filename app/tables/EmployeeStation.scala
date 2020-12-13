package tables

import slick.jdbc.PostgresProfile.api._
//import defaults ::



//todo: Employee Station   table
case class EmployeeStation(
                            id: Long = 0L,
                            employee_id: Long,
                            office_id: Long,
                            station_id: Long
                          )

class EmployeeStationTable(tag: Tag) extends Table[EmployeeStation](tag, "employee_station") {
  override def * = (id, employee_id, office_id, station_id) <> (EmployeeStation.tupled, EmployeeStation.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def employee_id = column[Long]("employee_id")

  def office_id = column[Long]("office_id")

  def station_id = column[Long]("station_id")
}

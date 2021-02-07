package tables


import slick.jdbc.PostgresProfile.api._
//import defaults ::

//todo: Stations    table
case class Station(
                    id: Long = 0L,
                    account_id: Long,
                    name: String,
                    code: String
                  )

class StationTable(tag: Tag) extends Table[Station](tag, "stations") {
  override def * = (id, account_id, name, code) <> (Station.tupled, Station.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def account_id = column[Long]("account_id")

  def name = column[String]("name")

  def code = column[String]("code")

}

package services

import java.sql.Connection

import entities.requests.kiosks.KioskRequest
import entities.responses.kiosks.KioskResponse

trait TKioskService {

  val tableName: String
  var conn: Connection

  //todo: create
  @_root_.scala.throws[Nothing]
  def create(owner: Integer, kiosk: KioskRequest): Unit

  //todo: Get All By Account
  def getAll(owner: Integer, offset: Long = 0, limit: Long = 10): Seq[KioskResponse]

  //todo: Get All By Account
  def getAllByStation(owner: Integer, stationId: Long, offset: Long = 0, limit: Long = 10): Seq[KioskResponse]

  //todo: get Station by Id
  @_root_.scala.throws[Nothing]
  def getById(owner: Integer, kioskId: Long): KioskResponse

  //todo: Archive Station
  def Archive(owner: Integer, stationId: Long): Unit

  //todo: Activate
  def Activate(owner: Integer, kioskId: Long): Unit

  //todo: check if kiosk exists
  def checkIfKioskExists(accountId: Integer, reference_id: String): Boolean
}

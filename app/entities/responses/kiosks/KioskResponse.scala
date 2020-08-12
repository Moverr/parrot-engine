package entities.responses.kiosks

import java.util._

case class KioskResponse(id: Long, reference_id: String, details: String, device_token: String, created_on: Date)

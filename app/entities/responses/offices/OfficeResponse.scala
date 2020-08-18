package entities.responses.offices

import java.util.Date

case class OfficeResponse(id: Long, reference_id: String, details: String, device_token: String, created_on: Date)

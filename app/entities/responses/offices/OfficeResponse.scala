package entities.responses.offices

import java.util.Date

case class OfficeResponse(id: Long,  name: String, parent_office:Long, created_on: Date)

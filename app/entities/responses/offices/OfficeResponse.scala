package entities.responses.offices

import java.util.Date

case class OfficeResponse(id: Long,  name: String, parent_office:String, created_on: Date)

package tables

import org.joda.time.DateTime
//import defaults ::

//todo:   user    table
case class User(id: Long = 0L, username: String, password: String, author_id: Long, created_on: DateTime, updated_by: Long, updated_on: Option[DateTime])


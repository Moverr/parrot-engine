package app.entities.responses

import org.joda.time.DateTime


case class AuthResponse(id:Long,username:String,token:String,dateCreated:DateTime)

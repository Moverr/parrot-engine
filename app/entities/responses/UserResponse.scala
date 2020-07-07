package app.entities.responses

import app.utils._

case class UserResponse(id:BigInt,status:StatusEnums.Value,firstname:String,lastname:String,role:RoleResponse,email:String,timezone:String,locale:String,avatar_url:String,phone_number:String)

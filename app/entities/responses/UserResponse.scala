package app.entities.responses

import app.utils.StatusEnums.StatusEnums

case class UserResponse(id:BigInt,status:StatusEnums.value,firstname:String,lastname:String,role:RoleResponse)

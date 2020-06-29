package app.dto

import anorm._

case class User (
                  id:Long,
                  username: String,
                  password: String
                )


object User extends Magic[User]
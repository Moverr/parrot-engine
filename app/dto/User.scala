package app.dto

import java.lang.annotation.Repeatable

case class User (
                  id:Long,
                  username: String,
                  password: String
                )


//object User extends Magic[User]
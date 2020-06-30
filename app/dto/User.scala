package app.dto

import javax.persistence._


case class User (
                  id:Long,
                  username: String,
                  password: String
                )


//object User extends Magic[User]
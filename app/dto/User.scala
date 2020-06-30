package app.dto

import javax.persistence._
import java.util


case class User (
                  id:Long,
                  username: String,
                  password: String
                )


//object User extends Magic[User]
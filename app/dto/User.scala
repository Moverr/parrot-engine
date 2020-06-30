package app.dto

import javax.persistence._
import java.util

@Entity
@Table(name="users")
case class User (
                  @Id
                  @GeneratedValue(strategy = GenerationType.IDENTITY)
                  @Column(name="user_id")
                  id:Long,
                  @Column(name="email")
                  username: String,
                  password: String
                )


//object User extends Magic[User]
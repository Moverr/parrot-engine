package app.dto

import java.sql.Timestamp

import javax.persistence._
import java.util

import com.sun.istack.internal.NotNull
//defaultPersistenceUnit
//Case Class
@Entity
@Table(name = "users",schema="default")
  case class User (
                 @Id
                 @GeneratedValue(strategy = GenerationType.IDENTITY)
                 @Column(name = "user_id")
                 id: Long,


                 @Basic(optional = false)
                 @Column(name = "username", length=255)
                 username: String,
                 @Basic(optional = false) @NotNull
                 @Column(name = "password",length=255)
                 password: String,
                 @Column(name = "author_id")
                 author_id: Integer,


                 @Basic(optional = false) @NotNull
                 @Column(name = "created_on") @Temporal(TemporalType.TIMESTAMP)
                 createdOn: Timestamp = null,

                 @Column(name = "join_date") @Temporal(TemporalType.TIMESTAMP)
                 joinDate: Timestamp = null,


                 @JoinColumn(name = "author_id", referencedColumnName = "id") @ManyToOne(optional = true)
                 private val author: User = null


               )


//object User extends Magic[User]
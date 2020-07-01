package app.dto

import javax.persistence._
import java.util
import com.sun.istack.internal.NotNull

@Entity
@Table(name = "users")
case class User(
                 @Id
                 @GeneratedValue(strategy = GenerationType.IDENTITY)
                 @Column(name = "user_id")
                 id: Long,


                 @Basic(optional = false) @NotNull @Size(min = 1, max = 255)
                 @Column(name = "username")
                 username: String,
                 @Basic(optional = false) @NotNull @Size(min = 1, max = 255)
                 @Column(name = "password")
                 password: String,
                 @Column(name = "author_id")
                 author_id: integer,


                 @Basic(optional = false) @NotNull
                 @Column(name = "created_on") @Temporal(TemporalType.TIMESTAMP)
                 createdOn: TIMESTAMP = null,

                 @Column(name = "join_date") @Temporal(TemporalType.TIMESTAMP)
                 joinDate: TIMESTAMP = null,


                 @JoinColumn(name = "author_id", referencedColumnName = "id") @ManyToOne(optional = true)
                 private val author: User = null


               )


//object User extends Magic[User]
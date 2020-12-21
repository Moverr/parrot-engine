package app.services

import javax.inject.Inject
import play.api.db.Database
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import services.traits.TUserService
import slick.jdbc.JdbcProfile
import tables.UserTable
import utils.HelperUtilities
//import defaults ::
//////


///////

class UsersService @Inject()(

                              @NamedDatabase("orders") ordersDatabase: Database,
                              dbConfigProvider: DatabaseConfigProvider ,
                              util: HelperUtilities) extends UserServiceTrait with TUserService {


  import dbConfig._
  import profile.api._


  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  private val table = TableQuery[UserTable]



}

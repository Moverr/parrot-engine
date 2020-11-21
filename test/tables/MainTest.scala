package tables

import org.joda.time.DateTime
import org.scalatest.FunSuite
import org.scalatest.flatspec.AnyFlatSpec
import tables.Main.Account

class MainTest extends AnyFlatSpec  {

  " Accounts Table " should "Initialize well " in {
    var account =   Account(1,1,"moee",DateTime.now(),null,1,1,"12345")
    assert(account.owner == 10)
  }


}

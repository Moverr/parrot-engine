package tables

import org.joda.time.DateTime
import org.scalatest.FunSuite
import org.scalatest.flatspec.AnyFlatSpec
import tables.Main.{Account, Department, Employee}

class MainTest extends AnyFlatSpec  {

  " Accounts Table " should "Initialize well " in {
    var account =   Account(1,1,"moee",DateTime.now(),null,1,1,"12345")
    assert(account.owner == 1)
  }

"Departments Table" should "Initialize well" in {
  var department = Department(0L,"Department One","CO",1,1)
  assert(department.office == 1)
}


  "Employee Table" should "Initialize well" in {
    var employee = Employee(1,"office","male",DateTime.now(),null,1,1)

    assert(employee.name  == "office")
  }




}

package tables

import java.sql.Date

import org.joda.time.DateTime
import org.scalatest.FunSuite
import org.scalatest.flatspec.AnyFlatSpec
import tables.Main._

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


  "Employee Station Table" should "Initialize well" in {
    var employeeStation = EmployeeStation(1,1,1,1)

    assert(employeeStation.id  == 1)
  }



  "Kiosk   Table" should "Initialize well" in {
    var kiosk = Kiosk(1,"reference","details","token",1,DateTime.now(),null,1,0L)

    assert(kiosk.id  == 1)
  }



  "Office   Table" should "Initialize well" in {
    var office =  Office(1,"office",Date,null,DateTime.now(),null,1,null,1)

    assert(office.id  == 1)
  }


  "Office   Table" should "Initialize well" in {
    var office =  Office(1,"office",Date,null,DateTime.now(),null,1,null,1)

    assert(office.id  == 1)
  }

  


}

package tables

import java.text.SimpleDateFormat

import org.joda.time.DateTime
import org.scalatest.FunSuite
import org.scalatest.flatspec.AnyFlatSpec
import tables.Main._

class MainTest extends AnyFlatSpec  {

  val dadte = "01/01/1990"
  var formate = new SimpleDateFormat("dd/MM/yyyy").parse(dadte)
  var aDate = new java.sql.Date(formate.getTime());



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
    var office =  Office(1,"office", aDate,null,DateTime.now(),null,1,null,1)
    assert(office.id  == 1)
  }


  "OfficeStation   Table" should "Initialize well" in {
    var officeStation =  OfficeStation(1,1,1)
    assert(officeStation.id  == 1)
  }




  "PermissionScope   Table" should "Initialize well" in {
    var permissionScope =  PermissionScope(1,"key","value")
    assert(permissionScope.id  == 1)
  }


  "Permission   Table" should "Initialize well" in {
    var permission =   Permission(1L,"permission","permission","grouping")
    assert(permission.grouping  equals("grouping") )
  }

  "Profile   Table" should "Initialize well" in {
    var profile =  Profile(1L,1L,"firstname","lastname",1L,DateTime.now(),1L,None)
    assert(profile.id ==1L )
  }






}

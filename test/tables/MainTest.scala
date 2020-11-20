package tables

import org.scalatest.FunSuite
import org.scalatest.flatspec.AnyFlatSpec

class MainTest extends AnyFlatSpec  {

  "Simple SEt Test" should "Aassert a zero synario" in {
    assert(Set.empty.size == 0)
  }

}

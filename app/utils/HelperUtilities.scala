package utils

import scala.util.Random

class HelperUtilities {

  def randomStringGenerator(size: Integer): String = {
    val x = Random.alphanumeric
    val result = x take (size)
    result.toString()
  }
}

object HelperUtilities extends HelperUtilities

//import scala.util.Random
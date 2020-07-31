package utils

import scala.util.Random

class HelperUtilities {

  def randomStringGenerator(size: Integer): String = {
    val x = Random.alphanumeric
    val result = x take (size)
    result.toString()
  }

  import java.util.Base64

  def convertToBasicAuth(username: String, Password: String): String = {
    val authString = username + ":" + Password
    val authEncBytes = Base64.getEncoder.encode(authString.getBytes)
    val authStringEnc = new String(authEncBytes)
    "Basic:" + authStringEnc
  }

  def decodeAuth(authentication: String): Array[String] = {
    val usernamePassword = new String(Base64.getDecoder.decode(authentication))
    val parts = usernamePassword.split(":")
    if (parts.length ne 2) {
      null
    } else parts
  }
}

object HelperUtilities extends HelperUtilities

//import scala.util.Random
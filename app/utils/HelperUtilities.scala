package utils

import scala.util.Random
import java.util.Base64


class HelperUtilities {

  def randomStringGenerator(size: Integer): String = {
    val x = Random.alphanumeric
    val result = x take (size)
    result.toString()
  }



  def convertToBasicAuth(username: String, Password: String): String = {
    val authString = username + ":" + Password
    val authEncBytes = Base64.getEncoder.encode(authString.getBytes)
    val authStringEnc = new String(authEncBytes)
    "bearer : " + authStringEnc
  }

  def decodeAuth(authentication: String): Array[String] = {
    val usernamePassword = new String(Base64.getDecoder.decode(authentication))
    val parts = usernamePassword.split(":")
    if (parts.length != 2) {
      null
    } else parts
  }
}

object HelperUtilities extends HelperUtilities

//import scala.util.Random
package app.utils

object SocialAuthType extends Enumeration {
  type SocialAuthType = Value
  val FACEBOOK = Value("FACEBOOK")
  val GOOGLE = Value("GOOGLE")
  val TWITTER = Value("TWITTER")

}

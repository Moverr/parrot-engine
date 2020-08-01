package app.entities.requests
import  app.utils._

case class SocialAuthentication(authType:SocialAuthType.Value, authtoken:String)
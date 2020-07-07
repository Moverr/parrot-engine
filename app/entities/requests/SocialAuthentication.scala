package app.entities.requests
import  app.utils.SocialAuthType

case class SocialAuthentication(authType:SocialAuthType,authtoken:String)
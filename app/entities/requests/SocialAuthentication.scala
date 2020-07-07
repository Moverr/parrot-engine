package app.entities.requests
import  app.utils.SocialAuthType

case class SocialAuthentication(authTyspe:SocialAuthType,authtoken:String)
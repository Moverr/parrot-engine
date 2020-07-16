package app.services

import app.entities.requests.AuthenticationRequest

class AuthService {
  def login(request: AuthenticationRequest): JsonValue = {
      implicit val roleResponserites = new Writes[AuthenticationRequest] {
        def writes(role: AuthenticationRequest) = Json.obj(
          "name" -> role.email,
          "age" -> role.age.toString()
        )

        val jsson = Json.toJson(formData)

        return jsson;
      }

  }
}

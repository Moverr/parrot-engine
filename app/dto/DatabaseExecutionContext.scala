package app.dto

import akka.actor.ActorSystem
import play.libs.concurrent.CustomExecutionContext
import javax.inject.Inject


class DatabaseExecutionContext extends CustomExecutionContext {
  def this(actorSystem: Nothing) {
    this()
    super (actorSystem, "database.dispatcher")
  }
}

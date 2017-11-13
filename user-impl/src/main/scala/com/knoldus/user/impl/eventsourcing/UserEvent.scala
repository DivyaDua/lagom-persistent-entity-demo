package com.knoldus.user.impl.eventsourcing

import com.knoldus.user.api.User
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

sealed trait UserEvent extends AggregateEvent[UserEvent] {
  override def aggregateTag: AggregateEventTagger[UserEvent] = UserEvent.Tag
}

object UserEvent {
  val Tag: AggregateEventTag[UserEvent] = AggregateEventTag[UserEvent]
}

case class UserCreated(user: User) extends UserEvent

object UserCreated{
  implicit val format: Format[UserCreated] = Json.format
}

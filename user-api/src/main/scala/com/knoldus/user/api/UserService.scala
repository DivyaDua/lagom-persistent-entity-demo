package com.knoldus.user.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait UserService extends Service {

  def createUser(id: String, name: String): ServiceCall[NotUsed, String]
  def getUser(id: String): ServiceCall[NotUsed, String]

  override def descriptor: Descriptor = {
    import Service._

    named("user_service")
      .withCalls(
        restCall(Method.POST, "/user/create/:id/:name", createUser _),
        restCall(Method.GET, "/user/details/:id", getUser _)
      ).withAutoAcl(true)
  }
}

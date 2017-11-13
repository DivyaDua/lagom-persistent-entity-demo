package com.knoldus.user.impl.service

import com.knoldus.user.api.UserService
import com.knoldus.user.impl.eventsourcing.UserEntity
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.softwaremill.macwire._
import play.api.libs.ws.ahc.AhcWSComponents

class UserServiceLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new UserServiceApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new UserServiceApplication(context) with LagomDevModeComponents
}

abstract class UserServiceApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  // Bind the service that this server provides
  override lazy val lagomServer = serverFor[UserService](wire[UserServiceImpl])

  //Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = UserSerializerRegistry

  // Register the lagom-persistent-entity-demo persistent entity
  persistentEntityRegistry.register(wire[UserEntity])
}


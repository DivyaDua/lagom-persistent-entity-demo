package com.knoldus.user.impl.service

import akka.{Done, NotUsed}
import com.knoldus.user.api.{User, UserService}
import com.knoldus.user.impl.eventsourcing._
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.{PersistentEntityRef, PersistentEntityRegistry}

import scala.concurrent.{ExecutionContext, Future}

class UserServiceImpl(persistentEntityRegistry: PersistentEntityRegistry)(implicit ec: ExecutionContext) extends UserService {

  override def createUser(id: String, name: String): ServiceCall[NotUsed, String] = {
    ServiceCall { _ =>
      val user = User(id, name)
      ref(user.id).ask(CreateUserCommand(user)).map {
        case Done => s"Hello $name! Your account has been created."
      }
    }
  }

  override def getUser(id: String): ServiceCall[NotUsed, String] = {
    ServiceCall { _ =>
      ref(id).ask(GetUserCommand(id)).map(user =>
        s"User for id:$id is ${user.name}")
    }
  }

  def ref(id: String): PersistentEntityRef[UserCommand[_]] = {
    persistentEntityRegistry
      .refFor[UserEntity](id)
  }

}

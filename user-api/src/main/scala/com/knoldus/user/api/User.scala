package com.knoldus.user.api

import play.api.libs.json.{Format, Json}

case class User(id: String, name: String)

object User{
  implicit val format: Format[User] = Json.format
}

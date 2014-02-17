package com.softwaremill.akka

import akka.actor.{Actor, ActorSystem}
import java.util.Random
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future

object AkkaDemo extends App {
  val actorSystem = ActorSystem("demo")

  implicit val timeout = Timeout(10.seconds)
  import actorSystem.dispatcher

  // todo

  actorSystem.shutdown()
  actorSystem.awaitTermination()
}

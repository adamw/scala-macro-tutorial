package com.softwaremill.akka

import akka.actor.{Actor, ActorSystem}
import java.util.Random
import akka.channels._
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future

object AkkaDemoComplete extends App {
  val actorSystem = ActorSystem("demo")

  implicit val dummySender: ChannelRef[(Any, Nothing) :+: TNil] = new ChannelRef(null)
  implicit val timeout = Timeout(10.seconds)
  import actorSystem.dispatcher

  val p = ChannelExt(actorSystem).actorOf(new Person, "p")

  val f1 = p <-?- Food()
  f1.map { r => println("f1 reply: " + r.value) }

  val f2: Future[Nothing] = p <-?- Money()
  f2.map { r => println("f2 reply: " + r) }

  Thread.sleep(1000L)

  actorSystem.shutdown()
  actorSystem.awaitTermination()
}

case class Food()
case class StillHungry()
case class Fed()

case class Money()

class Person extends Actor with Channels[TNil, (Food, StillHungry) :+: (Food, Fed) :+: (Money, Nothing) :+: TNil] {
  val r = new Random()

  channel[Food] { (req, snd) =>
    println("Got food!")
    if (r.nextBoolean()) {
      snd <-!- StillHungry()
    } else {
      snd <-!- Fed()
    }
  }

  channel[Money] { (req, snd) =>
    println("Got money!")
    // buy some stuff
  }
}


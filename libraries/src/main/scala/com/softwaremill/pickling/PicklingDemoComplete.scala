package com.softwaremill.pickling

object PicklingDemoComplete extends App {
  import scala.pickling._
  import json._

  //

  case class Person(name: String, age: Int)
  val bob = Person("Bob", 98)

  class CustomPersonPickler(implicit val format: PickleFormat) extends SPickler[Person] {
    def pickle(picklee: Person, builder: PBuilder) = {
      builder.beginEntry(picklee)
      builder.putField("n", b => b.hintTag(FastTypeTag.ScalaString).beginEntry(picklee.name).endEntry())
      builder.putField("a", b => b.hintTag(FastTypeTag.Int).beginEntry(picklee.age).endEntry())
      builder.endEntry()
    }
  }

  implicit def customPersonPickler(implicit format: PickleFormat) = new CustomPersonPickler()

  val pickledBob = bob.pickle
  println(pickledBob)

  //

  println()
  println()

  //

  case class Food(id: Int, name: String, healthy: Boolean)
  val fries = Food(1, "fries", healthy = false)

  val pickledFries = fries.pickle // auto-generated Pickler
  println(pickledFries)

  println()

  val unpickledPickledFires = pickledFries.unpickle[Food]
  println(unpickledPickledFires)
}

package com.softwaremill.async

import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}
import scala.concurrent.{Await, Future}
import java.util.Random
import scala.concurrent.duration._

object AsyncDemo extends App {
  val r = new Random()
  def sleep() = { Thread.sleep(r.nextInt(2000)) }
  def printResult[T](f: Future[T]) {
    val start = System.currentTimeMillis()
    val r = Await.result(f, 10.seconds)
    val end = System.currentTimeMillis()
    println(s"Result (after ${end-start}ms): $r")
  }

  // todo
}

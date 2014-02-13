package com.softwaremill.async

import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}
import scala.concurrent.{Await, Future}
import java.util.Random
import scala.concurrent.duration._

object AsyncDemoComplete extends App {
  val r = new Random()
  def sleep() = { Thread.sleep(r.nextInt(2000)) }
  def printResult[T](f: Future[T]) {
    val start = System.currentTimeMillis()
    val r = Await.result(f, 10.seconds)
    val end = System.currentTimeMillis()
    println(s"Result (after ${end-start}ms): $r")
  }

  {
    val f1 = Future { sleep(); 20 }
    val f2 = Future { sleep(); 22 }

    val f3: Future[Int] = for {
      v1 <- f1
      v2 <- f2
    } yield v1+v2

    printResult(f3)
  }

  {
    val f3: Future[Int] = async {
      val f1 = async { sleep(); 20 }
      val f2 = async { sleep(); 22 }

      await(f1) + await(f2)
    }

    printResult(f3)
  }

  {
    val f3: Future[Int] = async {
      val f1 = async { sleep(); r.nextBoolean() }
      val f2 = async { sleep(); 42 }

      if (await(f1)) await(f2) else 0
    }

    printResult(f3)
  }
}

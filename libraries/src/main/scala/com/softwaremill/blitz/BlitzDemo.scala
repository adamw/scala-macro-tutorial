package com.softwaremill.blitz

object BlitzDemo extends App {

  val largeList = (1 to 10000000).map(_.toDouble).toList

  println("Non-optimized")
  for (i <- 1 to 5) {
    bench {
      def average(x: List[Double]) = {
        x.sum / x.size
      }

      average(largeList)
    }
  }

  def bench(code: => Unit) {
    val start = System.currentTimeMillis()
    code
    val end = System.currentTimeMillis()
    println(s"Took: ${end-start}ms")
  }
}

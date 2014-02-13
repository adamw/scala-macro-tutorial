package com.softwaremill.slick

import scala.slick.driver.H2Driver.simple._
import scala.slick.direct._
import scala.slick.direct.AnnotationMapper._
import scala.slick.driver.H2Driver

object SlickDemoComplete extends App {

  val db = Database.forURL("jdbc:h2:mem:macrosdemo;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

  // Schema 1

  case class Food(id: Int, name: String, healthy: Boolean)

  class Foods(tag: Tag) extends Table[Food](tag, "foods") {
    def id      = column[Int]("id", O.PrimaryKey)
    def name    = column[String]("name")
    def healthy = column[Boolean]("healthy")

    def * = (id, name, healthy) <> (Food.tupled, Food.unapply)
  }
  val foods = TableQuery[Foods]

  db.withTransaction { implicit session =>
    foods.ddl.create
    foods += Food(1, "fries", healthy = false)
    foods += Food(2, "belgian beer", healthy = true)
    foods += Food(3, "mayonnaise", healthy = false)
  }

  db.withTransaction { implicit session =>
    println("Known unhealthy foods:")
    foods.filter(_.healthy =!= true).list().foreach(println)
    println()
  }

  db.withTransaction { implicit session =>
    println("Known healthy food:")
    val q = for {
      f <- foods if f.healthy === true
    } yield f
    q.firstOption().foreach(println)
    println()
  }

  // Schema 2
  val backend = new SlickBackend(H2Driver, AnnotationMapper)

  db.withTransaction { implicit session =>
    println("Known unhealthy foods:")
    val q = Queryable[DirectFood].filter(_.healthy == true)
    println(backend.result(q, session))
    println()
  }
}

@table("foods") case class DirectFood(
  @column("id") id: Int,
  @column("name") name: String,
  @column("healthy") healthy: Boolean)
package com.softwaremill.expecty

import org.expecty.Expecty

object ExpectyDemoComplete extends App {
  case class Name(firstName: String, lastName: String)
  case class Person(name: Name, age: Int) {
    def introduceTo(otherName: Name) = "Hello, " + otherName.firstName
  }

  val aPerson = Person(Name("bob", "doe"), 42)
  val otherName = Name("alice", "ash")

  // failEarly so that we can do two examples in a row
  val expect = new Expecty(failEarly = false)

  expect {
    aPerson.name.firstName == "john"
    aPerson.introduceTo(otherName) == "Yo, alice"
  }
}

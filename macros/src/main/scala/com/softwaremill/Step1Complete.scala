package com.softwaremill

// This import is needed for macros to be enabled at all
import language.experimental.macros

// Normal imports
import reflect.macros.blackbox.Context

object Step1Complete {
  def hello(): Unit = macro hello_impl

  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._
    println("Compiling!")
    reify { println("Hello World!") }
  }
}
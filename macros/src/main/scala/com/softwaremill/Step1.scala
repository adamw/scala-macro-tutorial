package com.softwaremill

// This import is needed for macros to be enabled at all
import language.experimental.macros

// Normal imports
import reflect.macros.Context

object Step1 {
  def hello(): Unit = macro hello_impl

  def hello_impl(c: Context)(): c.Expr[Unit] = {
    null
  }
}
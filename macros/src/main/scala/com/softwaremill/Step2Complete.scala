package com.softwaremill

import language.experimental.macros

import reflect.macros.blackbox.Context

object Step2Complete {
  def myPrintln(param: Any): Unit = macro myPrintln_impl

  def myPrintln_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._
    reify { println(param.splice) }
  }
}

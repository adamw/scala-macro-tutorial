package com.softwaremill

import language.experimental.macros

import reflect.macros.Context

object Step2 {
  def myPrintln(param: Any): Unit = macro myPrintln_impl

  def myPrintln_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    null
  }
}

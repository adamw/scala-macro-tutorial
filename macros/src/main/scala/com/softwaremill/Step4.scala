package com.softwaremill

import language.experimental.macros

import reflect.macros.blackbox.Context

object Step4 {
  def debug(param: Any): Unit = macro debug_impl

  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    null
  }
}

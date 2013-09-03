package com.softwaremill

import language.experimental.macros

import reflect.macros.Context

object Step3 {
  def debug(param: Any): Unit = macro debug_impl

  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    null
  }
}

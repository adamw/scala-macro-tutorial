package com.softwaremill

import language.experimental.macros

import reflect.macros.blackbox.Context

object Step4Complete {
  def debug(param: Any): Unit = macro debug_impl

  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._

    val paramRep = show(param.tree)
    c.Expr[Unit](q"""println($paramRep + "=" + $param)""")
  }
}

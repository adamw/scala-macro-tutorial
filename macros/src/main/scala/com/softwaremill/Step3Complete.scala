package com.softwaremill

import language.experimental.macros

import reflect.macros.Context

object Step3Complete {
  def debug(param: Any): Unit = macro debug_impl

  def debug_impl(c: Context)(param: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._

    println("Example println tree:")
    println(showRaw(reify { println("Hello World!") }.tree))

    val paramRep = show(param.tree)
    val paramRepTree = Literal(Constant(paramRep))
    val paramRepExpr = c.Expr[String](paramRepTree)
    reify { println(paramRepExpr.splice + " = " + param.splice) }
  }
}

package ru.korobeynikov.chapter4.chapter4_1

sealed class Expr {

    class Num(val value: Int) : Expr()

    class Sum(val left: Expr, val right: Expr) : Expr()
}
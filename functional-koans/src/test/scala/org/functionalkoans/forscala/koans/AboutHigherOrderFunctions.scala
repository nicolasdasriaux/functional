package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

/*
FUNCTIONS, FUNCTIONS EVERYWHERE

In Scala, functions are first class citizens.
Functions literal are also called lambda expressions.

Functions can be:
- assigned to vals (or to EVIL vars),
- returned by functions (or methods),
- passed as parameter to functions (or methods).
*/
class AboutHigherOrderFunctions extends KoanSuite {

  koan("A lambda is an expression that evaluates to an (anomymous) function. It can be assigned to a `val`") {
    val inc: Int => Int = (n: Int) => n + 1
    // Type of `inc` is mentioned explicitely.
    // `Int => Int` is the type of `inc`.
    // `(n: Int) => n + 1` is the lambda expression assigned to `inc`.

    val add: (Int, Int) => Int = (a: Int, b: Int) => a + b
    // Type of `add` is also mentioned explicitely.
    // `(Int, Int) => Int` is the type of `add`.
    // `(a: Int, b: Int) => a + b` is the lambda expression assigned to `add`.
  
    val incDesugarred = new Function1[Int, Int] {
      def apply(n: Int): Int = n + 1
    }

    val addDesugarred = new Function2[Int, Int, Int] {
      def apply(a: Int, b: Int): Int = a + b
    }

    val incValue = inc(1) // We chain lambda call on `inc`.
    val addValue = add(5, 4) // We chain lambda call on `add`.
    val incDesugarredValue = incDesugarred(1)
    val addDesugarredValue = addDesugarred(5, 4)

    incValue should be(__)
    addValue should be(__)
    incDesugarredValue should be(__)
    addDesugarredValue should be(__)
  }

  koan("When assigning a lambda expression to a `val`, type can be inferred as usual in Scala") {
    val inc = (n: Int) => n + 1 
    val add = (a: Int, b: Int) => a + b
 
    val incValue = inc(1)
    val addValue = add(5, 4)

    incValue should be(__)
    addValue should be(__)
  }

  koan("A lambda expression can be returned by a method without parameters") {
    def inc(): Int => Int = (x: Int) => x + 1
    // Return type of `inc` method is mentioned explicitely.
    // `inc` takes no parameter and `()` is REQUIRED to call it.
    // `Int => Int` is return type of `inc`.
    // `(n: Int) => n + 1` is the lambda expression returned by `inc`.

    def add = (a: Int, b: Int) => a + b
    // Return type of `add` method is inferred.
    // `add` takes no parameter and `()` is NOT REQUIRED to call it.
    // `(a: Int, b: Int) => a + b` is the lambda expression returned by `add`.

    val incValue = inc()(1) // `()` REQUIRED to call `inc`, then we chain call on lambda. Quite ugly!
    val addValue = add(5, 4) // `()` NOT REQUIRED to call `add`, then we chain call on lambda. Pretty!

    incValue should be(__)
    addValue should be(__)
  }

  koan("A lambda expression can be returned by a method with parameters") {
    def add(a: Int): Int => Int = (b: Int) => a + b
    // `add` takes an `a` parameter and return an lamdba taking a `b` parameter.

    val addFive: Int => Int = add(5)
    val addFiveValue = addFive(4)

    val addValue = add(5)(4) // We call `add` passing 5, then chain call on returned lambda passing 4.
  
    addFiveValue should be(__)
    addValue should be(__)
  }

  koan("A lambda expression can be passed as a method parameter") {
    def applyTwice(n: Int, f: Int => Int): Int = f(f(n))
    // `applyTwice` takes an `n` and an `f` function and applies `f` twice on `n`.

    val doubled = (n: Int) => n * 2

    val value1 = applyTwice(5, doubled) // passing a `val` holding an lambda expression
    val value2 = applyTwice(5, n => n * 2) // passing lambda expression directly (type of `n` is inferred)
    val value3 = applyTwice(5, _ * 2) // shortcut syntax

    value1 should be(__)
    value2 should be(__)
    value3 should be(__)
  }
}

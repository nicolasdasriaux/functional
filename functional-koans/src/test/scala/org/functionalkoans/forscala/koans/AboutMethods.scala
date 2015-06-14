package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

import scala.annotation.tailrec

class AboutMethods extends KoanSuite {

  koan( """A method's last statement will be what is returned.
          | There is no need for the keyword `return`.
          | `=` allows to assign the return value of the method.
          | Return type can be inferred from return value.""") {

    def add(x: Int, y: Int) = {
      // LAST EXPRESSION OF {} BLOCK IS THE VALUE OF THE BLOCK.
      x + y 
    }

    add(6, 7) should be(__)
  }

  /* For public methods, this is a best practice to explicitly mention return type. */
  koan(
    """If you want to include the return type explicitly,
      | no one will stop you""") {

    def add(x: Int, y: Int): Int = { // Type annotation for return type
      // LAST EXPRESSION OF {} BLOCK IS THE VALUE OF THE BLOCK.
      x + y
    }

    add(2, 10) should be(__)
  }


  koan("Last statement can be an if expression or any other expression") {
    // Here we do not use curly brackets because expression fits on one line, but we could.
    def min(x: Int, y: Int): Int = if (x < y) x else y

    min(5, 10) should be(__)
    min(8, 4) should be(__)
  }

  koan(
    """If method returns nothing, you have to mention `Unit` as the return type.
      | This is analogous to `void` in Java.""") {

    def check(x: Int): Unit = {
      (x * 2) should be(__) // Last line evaluates to `()` of type `Unit`.
    }

    check(3)
  }

  koan("A method can be declared to take no parameter and to be called WITHOUT `()`") {
    def number = 42

    number should be (__)
  }

  koan("A method can be declared to take no parameter and to be called WITH `()`") {
    def number() = 42

    number() should be (__)
  }

  koan("When performing recursion, the return type on the method is mandatory!") {

    def factorial(n: BigInt): BigInt = { // Notice the return type of BigInt!
      if (n <= 1)
        1
      else
        n * factorial(n - 1)
    }

    factorial(4) should be(__)

    //Note: Fire up a REPL and paste factorial(100000)!
  }

  koan(
    """If you want to ensure a method is not only recursive but _tail recursive_,
      | you can get help from the scala compiler to ensure that it is indeed a
      | tail recursive call by including `scala.annotation.tailrec` on the method.
      | When methods are properly tail recursive,
      | the Scala compiler will optimize the code
      | from stack recursion into a loop at compile time""") {

    import scala.annotation.tailrec // Importing annotation!

    @tailrec
    // Using an accumulator is a common pattern to get tail recursion.
    def fact(i: BigInt, accumulator: BigInt): BigInt = {
      if (i <= 1)
        accumulator
      else
        fact(i - 1, i * accumulator)
    }

    def factorial(n: BigInt): BigInt = fact(n, 1)


    factorial(4) should be(__)

    //Note: Fire up a REPL and try factorial(100000) now!
  }

  koan(
    """In Scala, methods can be placed inside of methods! This comes useful for
      | recursion where accumulator helper methods can be placed inside the outer
      | method, or you just want to place one method in another for design reasons""") {

    def factorial(i: BigInt): BigInt = {

      @tailrec
      def fact(i: BigInt, accumulator: BigInt): BigInt = {
        if (i <= 1)
          accumulator
        else
          fact(i - 1, i * accumulator)
      }

      fact(i, 1)
    }

    factorial(5) should be(__)
  }
}

package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

/*
EXPRESSIONS ARE PURE

An expression evaluates to a result.
An expression never performs any side effect in itself.

Expressions are easy to reason about,
because they do not depend on what happened before,
except if you mix them with variables,
mutables collections, methods with side effect...

So we are NOT going to want:
- variables,
- mutables collections,
- mutable objects,
- and any kind of side effects.

For IO side effects this is a bit more difficult
but we can isolate them to minimize the problem. 
*/
class AboutExpressions extends KoanSuite {

  koan("A block is an expression, its value is the expression on last line") {
    // A block expression
    val hello = {
      val name = "Paul"
      // LAST EXPRESSION OF {} BLOCK IS THE VALUE OF THE BLOCK.
      "Hello " + name + "!"
    }

    hello should be (__)
  }

  koan("if is an expression") {
    val temp = 18

    // An if expression
    val weather = if (temp > 14) "Warm" else "Cold"

    weather should be (__)
  }

  koan("match is an expression") {
    val mark = 3

    // A match expression
    val grade = mark match {
      case 1 => "Mediocre"
      case 2 => "Average"
      case 3 => "Good"
    }

    grade should be (__)
  }

  koan("try catch is an expression") {
    // A try catch expression
    val success = try {
      // LAST EXPRESSION OF {} BLOCK IS THE VALUE OF THE BLOCK.
      "Wow!"
    } catch {
      case ex: RuntimeException => "Boom!" // Value in case of any exception
    }

    // Another try catch expression
    val failure = try {
      throw new RuntimeException("FAILURE")
      // LAST EXPRESSION OF {} BLOCK IS THE VALUE OF THE BLOCK.
      "Yes!"
    } catch {
      case ex: RuntimeException => "Nooo!" // value in case of any exception
    }

    success should be (__)
    failure should be (__)
  }

  koan("Even for is an expression") {
    // Just a list of names
    val names = Seq("Paul", "Peter", "Mary")

    // A for expression yielding values
    // And now we get a list of greetings matching the names
    val greetings = for (name <- names) yield "Hello " + name + "!"

    greetings should be (Seq(__, __, __))
  }

  koan("Evaluation of a method (or function) is always an expression") {
    def max(a: Int, b: Int): Int = if (a < b) b else a

    max(5, 6) should be (__)
  }

  koan("Expressions can be combined to form expressions without even using var") {
    // Just a list of temperatures
    val temps = Seq(12, 18, 20)

    // A for expression yielding values
    // And we now get an list of weathers matching the temperatures after an adjustement
    val weathers = for (temp <- temps) yield {
      val adjustedTemp = temp - 2
      // LAST EXPRESSION OF {} BLOCK IS THE VALUE OF THE BLOCK.
      if (adjustedTemp > 14) "Warm" else "Cold"
    }

    weathers should be(Seq(__, __, __))
  }
}

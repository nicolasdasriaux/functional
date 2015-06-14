package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

import scala.collection._

/*
INSTRUCTIONS ARE IMPURE

An instruction evaluates to nothing.
An expression very often performs a side effect
(variable assignement, collection mutation, object mutation, jump, exceptions, IO).

Side effects are difficult to reason about,
because they depend on what happened before.
*/
class AboutInstructions extends KoanSuite {

  koan("if can be an instruction") {
    val temp = 18

    // VAR IS OFTEN A BAD SMELL
    // Local variable must have an initial value
    var weather: String = null

    // An if instruction performing
    if (temp > 14) {
      // DANGER SIDE EFFECT (variable assignment)
      weather = "Warm"
    } else {
      // DANGER SIDE EFFECT (variable assignment)
      weather = "Cold"
    }

    weather should be (__)
  }

  koan("match can be an instruction") {
    val mark = 3

    // Local variable must have an initial value
    // VAR IS OFTEN A BAD SMELL
    var grade: String = null

    // A match instruction
    mark match {
      case 1 =>
        // DANGER SIDE EFFECT (variable assignment)
        grade = "Mediocre"
      case 2 =>
        // DANGER SIDE EFFECT (variable assignment)
        grade = "Average"
      case 3 =>
        // DANGER SIDE EFFECT (variable assignment)
        grade = "Good"
    }

    grade should be (__)
  }

  koan("try catch can be an instruction") {
    // VAR IS OFTEN A BAD SMELL
    var success: String = null

    // A try catch instruction
    try {
      // DANGER SIDE EFFECT (variable assignment)
      success = "Wow!"
    } catch {
      case ex: RuntimeException =>
        // DANGER SIDE EFFECT (variable assignment)
        success = "Boom!"
    }

    // Local variable must have an initial value
    // VAR IS OFTEN A BAD SMELL
    var failure: String = null

    // Another try catch instruction
    try {
      throw new RuntimeException("FAILURE")
      // DANGER SIDE EFFECT (variable assignment)
      failure = "Yes!"
    } catch {
      case ex: RuntimeException =>
        // DANGER SIDE EFFECT (variable assignment)
        failure = "Nooo!"
    }

    success should be (__)
    failure should be (__)
  }

  koan("`for` can be an instruction yielding nothing") {
    // Just a list of names
    val names = Seq("Paul", "Peter", "Mary")

    // MUTABLE COLLECTION IS OFTEN A BAD SMELL 
    val greetings = mutable.ArrayBuffer.empty[String]

    // A for instruction yielding nothing
    for (name <- names) {
      // DANGER SIDE EFFECT (mutation of collection)
      greetings += "Hello " + name + "!"
    }

    greetings should be (Seq(__, __, __))
  }

  koan("return is an instruction, it's some kind of goto") {
    def max(a: Int, b: Int): Int = {
      if (a < b) {
        // DANGER SIDE EFFECT (jump)
        return b
      }

      // DANGER SIDE EFFECT (jump)
      return a
    }

    max(5, 6) should be (__)
  }

  koan("Instructions in fact evaluate to a secret value `()` of type `Unit`") {
    // Unit class has only one instance written as () 
    val unit: Unit = ()

    var a: Int = 0;

    val blockValue = {
      a = 10 // Assignment instuction, not an expression
    }

    blockValue.isInstanceOf[Unit] should be (__)
    blockValue should be (__)
  }
}

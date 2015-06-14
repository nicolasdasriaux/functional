package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

/*
VAL, IT'S A QUESTION OF VALUES

Functional style likes immutability,
including for local variables and parameters.

`val` in Scala is equivalent to `final`.
*/
class AboutVal extends KoanSuite {

  koan("val is for values, and may not be reassigned, analogous to final in Java") {
    val a: Int = 5 // Here type is explicitly mentioned with a type annotation

    a should be (__)

    // What happens if you uncomment these lines?
    // a = 7
    // a should be (7)
  }

  koan("Scala infers type for val (and also in many other occasions)") {
    val a = 5 // Usually we don't use type annotation in Scala

    a should be (__)
  }

  koan("var is for variable, and may be reassigned") {
    // We are functionally orthodox!
    // We will try avoiding vars as much as possible.
    // VAR (EVEN FOR LOCAL) IS OFTEN A BAD SMELL 
    var b = "Hello"

    b should be (__)

    // DANGER SIDE EFFECT (variable assignment)
    b = b + "!"

    b should be (__)
  }
}

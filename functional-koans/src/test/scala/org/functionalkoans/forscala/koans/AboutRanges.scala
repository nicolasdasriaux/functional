package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

/*
RANGES

Ranges are very usefull when used with `for`.
*/
class AboutRanges extends KoanSuite {

  koan("Range are not inclusive at end of range") {
    val nonInclusiveRange = Range(1, 10)

    nonInclusiveRange.size should be (__)

    nonInclusiveRange.contains(0) should be (__)
    nonInclusiveRange.contains(1) should be (__)
    nonInclusiveRange.contains(5) should be (__)
    nonInclusiveRange.contains(10) should be (__)
    nonInclusiveRange.contains(11) should be (__)
  }

  koan("Range can specify to include the last value") {
    val inclusiveRange = Range.inclusive(1, 10)

    inclusiveRange.size should be (__)

    inclusiveRange.contains(0) should be (__)
    inclusiveRange.contains(1) should be (__)
    inclusiveRange.contains(5) should be (__)
    inclusiveRange.contains(10) should be (__)
    inclusiveRange.contains(11) should be (__)
  }

  koan("Exclusive range can be created in a more readable way") {
    val r1 = Range(1, 10)

    val r2 = 1 until 10 
    // Calling `until` method without `.`, `(` and `)`

    (r1 == r2) should be (__)
  }

  koan("Inclusive range can be created in a more readable way") {
    val r1 = Range.inclusive(1, 10)

    val r2 = 1 to 10
    // Calling `to` method without `.`, `(` and `)`

    (r1 == r2) should be (__)
  }

  koan("Exclusive range can also mention a step") {
    val r1 = Range(start = 0, end = 34, step = 2)
    // Using parameter names to clarify use

    val r2 = 0 until 34 by 2
    // `to` and `by` are both methods called with stripped syntax

    (r1 == r2) should be (__)
  }

  koan("Inclusive range can also mention a step") {
    val r1 = Range.inclusive(start = 0, end = 34, step = 2)
    // Using parameter names to clarify use

    val r2 = 0 to 34 by 2
    // `to` and `by` are both method called with stripped syntax

    (r1 == r2) should be (__)
  }

  koan("""Ranges can be filtered
          | and return an indexed sequence""") {

    val numbers = (1 to 10).filter(_ < 4)

    numbers should be(IndexedSeq(__, __, __))
  }

  koan("""Ranges can be mapped over
          | and return an indexed sequence""") {

    val ranks = (1 to 3).map(i => s"Rank $i")

    ranks should be(IndexedSeq(__, __, __))
  }

  koan("""Ranges can be flat-mapped over
          | and return an indexed sequence""") {

    val numbers = (1 to 3).flatMap(i => 1 to i)
    // If you've got an headache, it's because that's not so legible.

    numbers should be(IndexedSeq(__, __, __, __, __, __))
  }

  koan("""Ranges can be used in a `for` comprehension
          | and return an indexed sequence""") {

    val trianglePoints = for {
      i <- 1 to 3 // first generator
      j <- 1 to i // second generator, varying more rapidly
    } yield (i, j)

    trianglePoints should be (IndexedSeq(__, __, __, __, __, __))
  }
}

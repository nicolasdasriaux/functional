package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite
import java.util.Date

/*
TUPLES

Tuples are a fundamental concept in most functional languages.
*/
class AboutTuples extends KoanSuite {

  koan("Tuples can be created easily") {
    val tuple = ("apple", "dog")

    tuple should be (__)
  }

  koan("Tuples support equality by value") {
    val t1 = ("apple", "dog")
    val t2 = ("pear", "cat")
    val t3 = ("apple", "dog")

    (t1 == t2) should be (__) // compare by value
    (t1 == t3) should be (__)
    (t1 eq t3) should be (__) // compare reference
  }

  // Not the preferred of accessing tuple attributes
  koan("Tuple items may be accessed individually") {
    val tuple = ("apple", "dog")
    val fruit = tuple._1
    val animal = tuple._2

    fruit should be (__)
    animal should be (__)
  }

  // Not the preferred of accessing tuple attributes
  koan("Tuples may be of mixed type") {
    val tuple5 = ("a", 1, 2.2, new Date(), BigDecimal(5))

    tuple5._2 should be (__)
    tuple5._5 should be (__)
  }

  koan("Tuples can be matched in assignements") {
    val tuple = ("cat", 5)

    val (specy, age) = tuple

    specy should be (__)
    age should be (__)
  }
}

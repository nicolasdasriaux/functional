package org.functionalkoans.forscala.koans

import  org.functionalkoans.forscala.support.KoanSuite

class AboutImmutableIndexedSequences extends KoanSuite {

  koan("Indexed sequence behaves like a function returning value at position") {
    val ids = IndexedSeq(1, 2, 3)

    ids(0) should be (__)
    ids(1) should be (__)
    ids(2) should be (__)

    intercept[IndexOutOfBoundsException] {
      ids(100)
    }
  }

  koan("Indexed sequence behaves like a partial function") {
    val ids = IndexedSeq(1, 2, 3)

    ids.isDefinedAt(0) should be (__)
    ids.isDefinedAt(1) should be (__)
    ids.isDefinedAt(2) should be (__)

    ids.isDefinedAt(100) should be (__)
  }

  koan("Two indexed sequences can be compared for equality") {
    val s1 = IndexedSeq(1, 2, 3)
    val s2 = IndexedSeq(1, 2, 3)
    val s3 = IndexedSeq(3, 2, 1)

    (s1 == s2) should be (__)
    (s1 == s3) should be (__)
    (s1 eq s2) should be (__)
  }

  koan("It can be tested whether or not a indexed sequence contains an element") {
    val s = IndexedSeq(1, 2, 3)

    s.contains(2) should be (__)
    s.contains(100) should be (__)
  }

  koan("""An element can be prepended to an indexed sequence
          | returning a new indexed sequence
          | without altering the original one""") {

    val ids = IndexedSeq(1, 2, 3)

    val newIds = 0 +: ids
    // Memory Tip: COLlection is on same side as the COLon.

    /*
    Why this cryptic operator?
    It could be a simple `prepend` method.
    `ids.prepend(0)`
    But with this syntax, order of operands is reversed.
    */

    newIds should be (__)
    ids should be (__)
  }

  koan("""An element can be appended to an indexed sequence
          | returning a new indexed sequence
          | without altering the original one""") {

    val ids = IndexedSeq(1, 2, 3)

    val newIds = ids :+ 4
    // Memory Tip: COLlection is on same side as the COLon.

    /*
    Why this cryptic operator?
    It could be a simple `append` method.
    `ids.append(4)`
    But with this syntax, this would be inconsistent with prepend operator.
    */

    newIds should be (__)
    ids should be (__)
  }

  koan("""Two indexed sequences can be concatenated)
          | returning a new indexed sequence
          | without altering the original ones""") {

    val s1 = IndexedSeq(1, 2, 3)
    val s2 = IndexedSeq(10, 20, 30)

    val s3 = s1 ++ s2

    s3 should be (__)
    s1 should be (__)
    s2 should be (__)
  }

  koan("""Indexed sequences can be filtered,
          | returning a new indexed sequence containing kept elements,
          | without altering the original one""") {

    val ids = IndexedSeq(1, 2, 3, 4)

    val filteredIds = ids.filter(_ > 2)

    filteredIds should be (__)
    ids should be (__)
  }

  koan("""Elements of an indexed sequence
          | can be individually applied a transformation
          | returning a new indexed sequence with these transformed elements,
          | without altering the original one""") {

    val names = IndexedSeq("Peter", "John", "Mary")

    val greetings = names.map(name => s"Hello $name!")

    greetings should be (__)
    names should be (__)
  }

  koan("""Mapping over an indexed sequence
          | with a transformation that returns an indexed sequence
          | will generate a indexed sequence containing indexed sequences.
          | This might not be what we want.""") {

    val numbers = IndexedSeq(1, 2, 3)

    val multipliedNumbers = numbers.map(n => IndexedSeq(n * 10, n * 100))

    multipliedNumbers should be (
      IndexedSeq(
        IndexedSeq(__, __),
        IndexedSeq(__, __),
        IndexedSeq(__, __)
      )
    )

    numbers should be (__)
  }

  koan("""When mapping over an indexed sequence
          | with a function that returns an indexed sequence
          | we might want to somehow flatten everything
          | and concatenate the indexed sequences""") {

    val numbers = IndexedSeq(1, 2, 3)
    val multipliedNumbers = numbers.flatMap(n => IndexedSeq(n * 10, n * 100))

    multipliedNumbers should be (IndexedSeq(__, __, __, __, __, __))
    numbers should be (__) // You got it now!
  }

  koan("""Indexed sequence may be used in a `for` comprehension
        | with one generator
        | and a transformation applied to items""") {

    val numbers = IndexedSeq(1, 2, 3, 4)

    val multipliedNumbers1 = for {
      n <- numbers // just one generator
    } yield n * 10

    // Alternatively a more compact syntax
    val multipliedNumbers2 = for (n <- numbers) yield n * 10

    multipliedNumbers1 should be (__)
    multipliedNumbers2 should be (__)

    /*
    Awareness awakener
    It looks equivalent to `map`, no?
    */
  }

  koan("""Indexed sequence may be used in a `for` comprehension
        | with one generator,
        | a filter condition,
        | and no transformation applied to items""") {

    val numbers = IndexedSeq(1, 2, 3, 4)

    val filteredNumbers1 = for {
      n <- numbers // just one generator
      if n % 2 == 0 // just one filter
    } yield n

    // Alternatively a more compact syntax
    val filteredNumbers2 = for (n <- numbers if n % 2 == 0) yield n

    filteredNumbers1 should be (__)
    filteredNumbers2 should be (__)

    /*
    Awareness awakener
    It looks equivalent to `filter`, no?
    */
  }

  koan("""Indexed sequence can be used in a `for` comprehension
        | with two generators
        | and second generator varying more rapidly""") {

    val numbers1 = IndexedSeq(1, 2, 3)
    val numbers2 = IndexedSeq(10, 20)

    val addedNumbers = for {
      n1 <- numbers1 // first generator
      n2 <- numbers2 // second generator, varying more rapidly
    } yield n1 + n2

    // Alternative short syntax is not recommended here

    addedNumbers should be (__)

    /*
    Awareness awakener
    Maybe we could write the equivalent
    with `flatMap` and `map`, no?
    */
  }

  koan("""Indexed sequence can be used in a `for` comprehension
        | combining everything""") {

    val numbers1 = IndexedSeq(1, 2, 3, 4)
    val numbers2 = IndexedSeq(10, 20, 30)

    val addedNumbers = for {
      n1 <- numbers1 // first generator
      if n1 % 2 == 0 // first filter
      n2 <- numbers2 // second generator, varying more rapidly
      if n2 < 30 // second filter
    } yield n1 + n2

    addedNumbers should be (__)

    /*
    Awareness awakener
    Maybe we could write the equivalent
    with `flatMap`, `map` and `filter`, no?
    */
  }
}

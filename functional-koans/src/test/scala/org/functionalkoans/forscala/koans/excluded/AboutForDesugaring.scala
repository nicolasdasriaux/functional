package org.functionalkoans.forscala.koans.excluded

import org.functionalkoans.forscala.support.KoanSuite

class AboutForDesugaring extends KoanSuite {

  koan("Elements of range can be filtered using for comprehension") {
    val ids: IndexedSeq[Int] = for {
      i <- 1 to 10
      if i < 4
    } yield i

    ids should be (IndexedSeq(__, __, __))
  }

  koan("""Alternatively, elements of range can be filtered
          |    and generate an indexed sequence using filter""") {

    val ids: IndexedSeq[Int] = (1 to 10).filter(i => i < 4)

    ids should be (IndexedSeq(__, __, __))
  }

  koan("Elements of range can transformed into indexed sequence using for comprehension") {
    val ids: IndexedSeq[String] = for (i <- 1 to 3) yield i.toString

    ids should be (IndexedSeq(__, __, __))
  }

  koan("Alternatively, elements of range can transformed into indexed sequence using map") {
    val ids: IndexedSeq[String] = (1 to 3).map(i => i.toString)

    ids should be (IndexedSeq(__, __, __))
  }

  koan("""It is possible to iterate over two nested ranges
          |    and generate an indexed sequence using for comprehension""") {

    val ids: IndexedSeq[String] = for {
      i <- 1 to 3 // equivalent to loop (outer)
      j <- 1 to i // equivalent to nested loop (inner)
    } yield i.toString + "-" + j.toString

    ids should be (IndexedSeq(__, __, __, __, __, __))
  }

  koan("""Alternatively, it is possible to iterate over two nested ranges
          |    and generate an indexed sequence using nested flatMap and map""") {

    // Desugared for comprehesion equivalent to previous for comprehension
    // NOT RECOMMENDED because of poor legibility
    // As a best practise, for comprehension is preferred in that case.

    val ids: IndexedSeq[String] =
      (1 to 3).flatMap({ i =>
        (1 to i).map({ j =>
          i.toString + "-" + j.toString
        })
      })

    ids should be (IndexedSeq(__, __, __, __, __, __))
  }

    koan("""IndexedSeq can be used in desugared for comprehension
          | with two generators""") {

    val numbers1 = IndexedSeq(1, 2, 3)
    val numbers2 = IndexedSeq(10, 20, 30)

    val addedNumbers =
      numbers1.flatMap({ n1 => // first generator
        numbers2.map({ n2 => // second generator
          n1 + n2
        })
      })

    addedNumbers should be (__)
  }


  koan("Option can be used in desugared for comprehension") {
    def fullName(firstName: Option[String], lastName: Option[String]) = {
      firstName.flatMap { _firstName =>
        lastName.map { _lastName =>
          s"${_firstName} ${_lastName}"
        }
      }
    }

    fullName(None, None) should be (__)
    fullName(None, Some("Dupond")) should be (__)
    fullName(Some("Pierre"), None) should be (__)
    fullName(Some("Pierre"), Some("Dupond")) should be (__)
  }


  koan("Sets can be used in desugared for comprehension") {
    val firstNames = Set("Peter", "John", "Mary")
    val lastNames = Set("Jones", "Simpson")

    val fullNames =
      firstNames.flatMap({ firstName =>
        lastNames.map({ lastName =>
          s"$firstName $lastName"
        })
      })

    fullNames should be (__)
  }

  koan("Maps can be used in desugared for comprehension") {
    val firstNameById = Map(1 -> "Peter", 2 -> "John", 3 -> "Mary")
    val lastNameById = Map(1 -> "Jones", 2 -> "Simpson")

    val fullNameById =
      firstNameById.flatMap({ case (firstNameId, firstName) =>
        lastNameById.map({ case (lastNameId, lastName) =>
          firstNameId + lastNameId * 10 -> s"$firstName $lastName"
        })
      })

    fullNameById should be (__)
  }
}

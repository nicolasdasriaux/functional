package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

/*
CASE CLASSES

Case classes offer a very concise syntax
that implement lots of utility method as a default.
They are very commonly used when programming in functional style.

They can be 'pattern-matched' (more on this latter).
*/
class AboutImmutableCaseClasses extends KoanSuite {

  koan("Case classes have an automatic equals method that works") {
    case class Person(firstName: String, lastName: String)

    val p1 = new Person("Fred", "Jones")
    val p2 = new Person("Shaggy", "Rogers")
    val p3 = new Person("Fred", "Jones")

    (p1 == p2) should be (__)
    (p1 == p3) should be (__)

    (p1 eq p2) should be (__)
    (p1 eq p3) should be (__) // not identical, merely equal
  }

  koan("Case classes have an automatic hashcode method that works") {
    case class Person(firstName: String, lastName: String)

    val p1 = new Person("Fred", "Jones")
    val p2 = new Person("Shaggy", "Rogers")
    val p3 = new Person("Fred", "Jones")

    (p1.hashCode == p2.hashCode) should be (__)
    (p1.hashCode == p3.hashCode) should be (__)
  }

  koan("Case classes have a convenient way they can be created") {
    case class Dog(name: String, breed: String)

    val d1 = Dog("Scooby", "Doberman")
    val d2 = Dog("Rex", "Custom")
    val d3 = new Dog("Scooby", "Doberman") // the old way of creating using new

    (d1 == d3) should be (__)
    (d1 == d2) should be (__)
    (d2 == d3) should be (__)
  }

  koan("It is desirable to check constructor parameters to ensure consistent object creation") {
    case class Person(id: Int, name: String) {
      require(id > 0, "ID should be a positive integer")
      require(name != null && !name.isEmpty, "Name should not be null or empty")
    }

    val p1 = Person(1, "Paul")
  
    intercept[IllegalArgumentException] {
      val p2 = Person(0, "Paul")
    }
  
    intercept[IllegalArgumentException] {
      val p3 = Person(1, null)
    }
  }

  koan("Case classes have a convenient toString method defined") {
    case class Dog(name: String, breed: String)

    val d1 = Dog("Scooby", "Doberman")

    d1.toString should be (__)
  }

  koan("Case classes have automatic properties") {
    case class Dog(name: String, breed: String)

    val d1 = Dog("Scooby", "Doberman")

    d1.name should be (__)
    d1.breed should be (__)

    // what happens if you uncomment the line below? Why?
    //d1.name = "Scooby Doo"
  }

  koan("Safer alternatives exist for 'altering' case classes") {
    case class Dog(name: String, breed: String)

    val d1 = Dog("Scooby", "Doberman")

    val d2 = d1.copy(name = "Scooby Doo") // copy the case class but change the name in the copy

    d1.name should be (__) // original left untouched
    d1.breed should be (__)

    d2.name should be (__)
    d2.breed should be (__) // copied from the original
  }

  koan("Safer alternatives exist for deeply 'altering' case classes") {
    case class Person(firstName: String, lastName: String, dog: Dog)
    case class Dog(name: String, breed: String)

    val p1 = Person("Paul", "Simpson", Dog("Rex", "Doberman"))

    val p2 = p1.copy(dog = p1.dog.copy(breed = "Great Dane")) // copy the case class but change the name in the copy

    p1.dog.name should be (__) // original left alone
    p1.dog.breed should be (__)

    p2.dog.name should be (__) // copied from the original
    p2.dog.breed should be (__)
  }

  case class Person(firstName: String, lastName: String, age: Int = 0, ssn: String = "")

  koan("Case classes have named parameters with default (as any method in Scala)") {
    val p1 = Person("Fred", "Jones", 23)
    val p2 = Person("Samantha", "Jones") // note missing age and ssn
    val p3 = Person(lastName = "Jones", firstName = "Fred", ssn = "111-22-3333") // note the order can change, and missing age
    val p4 = p3.copy(age = 23)

    p1.firstName should be (__)
    p1.lastName should be (__)
    p1.age should be (__)
    p1.ssn should be (__)

    p2.firstName should be (__)
    p2.lastName should be (__)
    p2.age should be (__)
    p2.ssn should be (__)

    p3.firstName should be (__)
    p3.lastName should be (__)
    p3.age should be (__)
    p3.ssn should be (__)

    (p1 == p4) should be (__)
  }

  koan("Case classes can be disassembled to their constituent parts as a tuple") {
    val p1 = Person("Fred", "Jones", 23, "111-22-3333")

    val parts = Person.unapply(p1).get // this seems weird, but it's critical to other features of Scala

    parts._1 should be (__)
    parts._2 should be (__)
    parts._3 should be (__)
    parts._4 should be (__)
  }
}

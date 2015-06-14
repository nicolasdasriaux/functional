package org.functionalkoans.forscala.koans

import org.functionalkoans.forscala.support.KoanSuite

/*
IMMUTABLE CLASSES ARE FUNCTIONAL STYLE

Scala has very concise syntax 
for simple (and advanced) immutable classes.
*/
class AboutClasses extends KoanSuite {

  // You can define a class with `val` parameter.
  class ClassWithValParameter(val name: String)

  koan("`val` parameters in class definition define getter") {
    val instance = new ClassWithValParameter("name")
    instance.name should be(__)
  }

  // You can define a class with `var` parameter.
  // MUTABLE CLASS IS OFTEN A BAD SMELL
  class ClassWithVarParameter(var description: String)

  koan("`var` parameters in class definition define getter and setter") {
    val instance = new ClassWithVarParameter("description")

    instance.description should be(__)

    // DANGER SIDE EFFECT (changing state of object)
    instance.description = "new description"

    instance.description should be(__)
  }

  // You can just define a parameter for constructing an instance
  class ClassWithPureParameter(name: String)

  koan("Parameters not prefixed with `val`or `var` are just pure parameters when constructing instance") {
    val instance = new ClassWithPureParameter("name")

    // instance.name does not exist
  }

  // `firstName` and `lastName` will define two getters
  class Person(val firstName: String, val lastName: String) {
    // `fullName` will expose a calculated getter
    def fullName = firstName + " " + lastName
  }

  koan("You also implement an immutable class with calculated getter") {
    val person = new Person("Paul", "Simpson")

    person.firstName should be (__)
    person.lastName should be (__)
    person.fullName should be (__)
  }

  // MUTABLE CLASS IS OFTEN A BAD SMELL
  // `initial` is just a parameter for the constructor
  class Counter(initial: Int) {
    // Private `_current` variable initialized with `initial` parameter
    private var _current = initial

    // Methods called conventionally WITHOUT `()` because performing NO SIDE EFFECT
    def current = _current

    // Method called conventionally WITH `()` because performing SIDE EFFECT 
    def inc(): Unit = {
      _current += 1
    }
  }

  koan("You can also implement a mutable class (though not functional style but object style)") {
    val counter = new Counter(1)

    counter.current should be (__)

    // DANGER SIDE EFFECT (changing state of object)
    counter.inc()

    counter.current should be (__)
  }
}

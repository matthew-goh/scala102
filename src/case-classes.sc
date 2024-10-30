case class Car(make: String, model: String, year: Int)
val bestCar = Car("Mazda", "RX-7 FD3S", 2004) // new keyword not needed!
val closeSecond = Car("Nissan", "GT-R R32", 1994)

// apply method is a constructor: takes arguments and creates the object
// unapply method is an extractor: takes an object and gives back the arguments (used in pattern matching)
// compared by structure, not by reference: 2 objects with the same arg values are overall equal in value (== is true)
// constructor parameters are val fields by default

case class Aircraft(name: String, inService: Boolean, noBuild: Int)
val current = Aircraft("A-10 Thunderbolt II", true, 716)
val future = current.copy(inService = false) // create a shallow copy, can change constructor arguments

case object Ketchup
case object Mustard

///////////////////////
// Slide 9 exercise
case class Dog(name: String, breed: String, age: Int)
val d1 = Dog("Alice", "poodle", 5)
val d2 = Dog("Bruce", "bulldog", 7)
val d3 = Dog("Ryan", "golden retriever", 4)
val d4 = Dog("Minnie", "beagle", 11)

case class Kennel(name: String, occupants: List[Dog])
val k1 = Kennel("Main Kennel", List(d1, d2, d3, d4))

// Slide 10
// change kennel name, keep dogs
val k2 = k1.copy(name = "Big Kennel")
// add cats and birds to the kennel case class
case class Cat(name: String, colour: String, age: Int)
case class Bird(name: String, variety: String, age: Int)
case class BigKennel(name: String, dogs: List[Dog], cats: List[Cat], birds: List[Bird])
val b1 = Bird("Henry", "penguin", 13)
val k2 = BigKennel("Home", List(d1, d2, d3, d4), List(), List(b1))

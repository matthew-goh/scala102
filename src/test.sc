case class Car(make: String, model: String, year: Int)
val bestCar = Car("Mazda", "RX-7 FD3S", 2004) // new keyword not needed!
val closeSecond = Car("Nissan", "GT-R R32", 1994)

// apply method is a constructor: takes arguments and creates the object
// unapply method is an extractor: takes an object and gives back the arguments (used in pattern matching)
// compared by structure, not by reference: 2 objects with the same arg values are overall equal in value (== is true)

case class Aircraft(name: String, inService: Boolean, noBuild: Int)
val current = Aircraft("A-10 Thunderbolt II", true, 716)
val future = current.copy(inService = false) // create a shallow copy, can change constructor arguments
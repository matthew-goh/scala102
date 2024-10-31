// Create a function that takes a String and returns a capitalised version of the String
// if it is a capital city of the UK, (London, Edinburgh, Cardiff and Belfast)
// or “<String> isn’t a UK capital” if it isn’t
def capitaliseCapitals(city: String) : String = {
  val cityLowercase = city.trim.toLowerCase()
  cityLowercase match {
    case "london" | "edinburgh" | "cardiff" | "belfast" =>
      city.toUpperCase()
    case _ =>
      s"$city isn't a UK capital"
  }
}
capitaliseCapitals("LonDon")
capitaliseCapitals("paris")

////////////////////////
// Create an Animal abstract class (define name and age vals within the class, not as a parameter)
abstract class Animal {
  val name: String
  val age: Int
}

// Create a Dog case class with name and age (extend the Animal abstract class)
// Create a Cat case class with name and age (extend the Animal abstract class)
case class Dog(name: String, age: Int) extends Animal
case class Cat(name: String, age: Int) extends Animal

// Write a function that checks the type of “Animal” and returns “Dog” for a dog, “Kitty” for a cat and “Other” for anything else
def animalType(animal: Animal) : String = {
  animal match {
    case Dog(_, _) => "Dog"
    case Cat(_, _) => "Kitty"
    case _ => "Other"
  }
}
animalType(Cat("Sam", 5)) // Kitty

// Write a function that checks if the animal is called Sam and returns
// “Sam’s age is: <age>” if the animal’s name is “Sam”
// or “<name> is not Sam” for anything else
def isAnimalNameSam(animal: Animal) : String = {
  animal.name match {
    case "Sam" => s"Sam's age is ${animal.age}"
    case _ => s"${animal.name} is not Sam"
  }
}
isAnimalNameSam(Cat("Sam", 7))
isAnimalNameSam(Dog("Sally", 5))

// Write a function that returns “<name> is <age> years old”
// if the animal is older than 10, or “<name> is young” for anything else
def animalAge(animal: Animal) : String = {
  if (animal.age > 10){
    s"${animal.name} is ${animal.age} years old"
  } else {
    s"${animal.name} is young"
  }
}
animalAge(Cat("Sam", 7))
animalAge(Dog("Sally", 11))

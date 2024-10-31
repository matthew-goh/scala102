object TypeOfAnimalEnum extends Enumeration{
  val Mammal, Reptile, Amphibian, Bird, Fish, Insect = Value
}

sealed trait TypeOfAnimal
case object Mammal extends TypeOfAnimal
case object Reptile extends TypeOfAnimal
case object Amphibian extends TypeOfAnimal
case object Bird extends TypeOfAnimal
case object Fish extends TypeOfAnimal
case object Insect extends TypeOfAnimal

case class Animal1(val name: String, val animalType: TypeOfAnimalEnum.Value)
case class Animal2(val name: String, val animalType: TypeOfAnimal)

// Use pattern matching to describe the animal
def describeAnimal1(animal: Animal1): String = {
  var article = "a"
  animal.animalType match {
    case TypeOfAnimalEnum.Amphibian | TypeOfAnimalEnum.Insect =>
      article = "an"
    case _ => // do nothing here
  }
  s"${animal.name} is $article ${animal.animalType.toString.toLowerCase}."
}
describeAnimal1(Animal1("Ann", TypeOfAnimalEnum.Mammal))
describeAnimal1(Animal1("Sam", TypeOfAnimalEnum.Amphibian))

def describeAnimal2(animal: Animal2): String = {
  animal.animalType match {
    case Mammal => s"${animal.name} is a mammal."
  }
  // etc
}
describeAnimal2(Animal2("Ann", Mammal))

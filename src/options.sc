trait Chocolate {
  val colour: String
  val filling: Option[String] // Some(<value>) or None
}

case class Bounty(colour: String, filling: Option[String]) extends Chocolate
case class DairyMilk(colour: String, filling: Option[String]) extends Chocolate
case class Caramel(colour: String, filling: Option[String]) extends Chocolate

val bounty = Bounty("brown", Some("coconut"))
val whiteDairy = DairyMilk("white", None)
val darkDairy = DairyMilk("dark brown", None)
val caramel = Caramel("brown", Some("caramel"))

// pattern matching on options
def whatsInTheChoc(chocolate: Chocolate): String = {
  chocolate.filling match {
    case Some("caramel") => "It’s caramel!"
    case Some(other) => s"No caramel, but $other" // matching the inner string of Some()
    case None => "It’s chocolate all the way down!"
  }
}

// access contents of an option
val filledOption: Option[Int] = Some(2)
val emptyOption: Option[Int] = None
val two: Int = filledOption.get // 2 // bad practice!
val number: Int = emptyOption.getOrElse(2) // 2 if option is None

////////////////////
// Slide 33
// Make a chocolate bar case class with an optional filling.
// Write a method to return the filling of the chocolate bar.
case class ChocolateBar(filling: Option[String])
def getFilling(bar: ChocolateBar): String = {
  bar.filling match {
    case Some(any) => s"The filling is $any!"
    case None => "No filling"
  }
}
getFilling(ChocolateBar(Some("mousse")))
getFilling(ChocolateBar(None))

////////////////////
// Slide 34
// Create a case class Dog, with as many optional parameters as you think necessary
// Write a function to return the colour of the Dog’s spots, or “No spots” if it has none.
// Try two implementations using pattern matching and getOrElse().
case class Dog(spotColour: Option[String])
def getSpotColour(dog: Dog): String = {
  dog.spotColour match {
    case Some(colour) => colour
    case None => "No spots"
  }
}
def getSpotColour2(dog: Dog): String = dog.spotColour.getOrElse("No spots")
getSpotColour(Dog(Some("red")))
getSpotColour2(Dog(None))

// Write a function that takes an Option[Int], and either returns the option value multiplied by 2: Some(doubledValue),
// or None if the value is not supplied
def doubleOptionInt(num: Option[Int]): Option[Int] = {
  num match {
    case Some(n) => Some(n*2)
    case None => None
  }
}
doubleOptionInt(Some(43))
doubleOptionInt(None)

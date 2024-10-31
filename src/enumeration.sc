object Weekday extends Enumeration {
  val Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday = Value
}

// can be serialised and deserialised
Weekday.Monday.toString // String = Monday
Weekday.withName("Monday") // Weekday.Value = Monday
//Weekday.withName("Mondai") // NoSuchElementException

// unique values
object Weekday extends Enumeration {
  val Monday = Value("Mon")
  val Tuesday = Value("Tue")
  val Wednesday = Value("Wed")
  val Thursday = Value("Thur")
  val Friday = Value("Fri")
  val Saturday = Value("Sat")
  val Sunday = Value("Sun")
}
Weekday.Monday.toString // String = Mon
//Weekday.withName("Monday") // NoSuchElementException
Weekday.withName("Mon") // Weekday.Value = Mon

// list all values of an enumeration type
Weekday.values // Weekday.ValueSet(Mon, Tue, Wed, Thur, Fri, Sat, Sun)

// values are inherently ordered
Weekday.Monday < Weekday.Tuesday // true
Weekday.Sunday > Weekday.Thursday // true

// ordering can be modified using numbered values
object Weekdays extends Enumeration {
  val Monday = Value(6)
  val Tuesday = Value(5)
  val Wednesday = Value(4)
  val Thursday = Value(3)
  val Friday = Value(2)
  val Saturday = Value(1)
  val Sunday = Value(0)
}
Weekdays.values
Weekdays.values.toList.sorted
// List(Sunday, Saturday, Friday, Thursday, Wednesday, Tuesday, Monday)
Weekdays.Sunday > Weekdays.Thursday // false

// pattern matching is non-exhaustive
// Compiler won’t show that not all values are matched
def nonExhaustive(weekday: Weekday.Value): Unit = {
  weekday match {
    case Weekday.Monday => println("I hate Mondays")
    case Weekday.Sunday => println("The weekend is already over? :(")
  }
}


// Enums have the same type after erasure (no more double definition error?)
object OtherEnum extends Enumeration {
  val A, B, C = Value
}

def test(enum: Weekday.Value): Unit = {
  println(s"enum: $enum")
}
def test(enum: OtherEnum.Value): Unit = {
  println(s"enum: $enum")
}

//test(Weekday.Monday) // type mismatch
//test(OtherEnum.B)

//////////////
// Can also use case objects to define finite sets
sealed trait Weekday
case object Monday extends Weekday
case object Tuesday extends Weekday
case object Wednesday extends Weekday
case object Thursday extends Weekday
case object Friday extends Weekday
case object Saturday extends Weekday
case object Sunday extends Weekday

// console warning: match may not be exhaustive
def exhaustive(weekday: Weekday): Unit = {
  weekday match {
    case Monday => println("I hate Mondays")
    case Sunday => println("The weekend is already over? :(")
  }
}
// this is one way to see all values of the enum (trait must be sealed)

// Can supply extra fields, but...
//No simple way to retrieve all Enumeration values
//No default serialise or deserialise methods
//No default ordering on Enumeration value - can be added manually in fields
abstract class Error(val status: String, val message: String, val order: Int) {
  def compare(other: Error) = this.order - other.order
}
case object InternalServer extends Error("SERVER_ERROR", "An internal server error occurred", 0)
case object NotFound extends Error("NOT_FOUND", "Matching resource was not found", 1)

// Write a function that accepts a string for a general ice cream flavour
// and returns a specific ben and jerry's flavour for each one:
// Caramel = caramel chew chew, Chocolate = chocolate fudge brownie, Cookie = cookie dough,
// anything else return original flavour

def BenAndJerry(flavour: String) : String = {
  flavour match {
    case "Caramel" => "caramel chew chew"
    case "Chocolate" => "chocolate fudge brownie"
    case "Cookie" => "cookie dough"
    case _ => "original"
  }
}
BenAndJerry("Chocolate")
BenAndJerry("Vanilla")

trait Flavour
case object CaramelChewChew extends Flavour
case object ChocolateFudgeBrownie extends Flavour
case object CookieDough extends Flavour
case object Original extends Flavour

def BenAndJerry2(flavour: String) : Flavour = {
  flavour match {
    case "Caramel" => CaramelChewChew
    case "Chocolate" => ChocolateFudgeBrownie
    case "Cookie" => CookieDough
    case _ => Original
  }
}
BenAndJerry2("Cookie")
BenAndJerry2("Vanilla")


// Pizza measurement to size: 7 = Personal, 9 = Small, 11 = Medium, 14 = Large,
// with Medium as our default size
// Pizza crusts: classic, italian, stuffed (+2.99 to the pizza cost).
// Some pizzas can only have certain crusts.
// Write a function that takes pizza size + crust as parameters, then calculates the cost:
//Personal 5.99: classic
//Small 10.99: classic, italian
//Medium 12.99: classic, italian, stuffed + 2.99
//Large 14.99: classic, italian, stuffed + 2.99

case class Pizza(size: Int = 11) // default medium

trait PizzaSize
case object Personal extends PizzaSize
case object Small extends PizzaSize
case object Medium extends PizzaSize
case object Large extends PizzaSize
case object Invalid extends PizzaSize

def pizzaMeasurementToSize(pizzaInp: Any) : PizzaSize = {
  pizzaInp match {
    case Pizza(size) if size == 7 => Personal
    case Pizza(size) if size == 9 => Small
    case Pizza(size) if size == 11 => Medium
    case Pizza(size) if size == 14 => Large
    case _ => Invalid
  }
}
val myPizza = Pizza()
pizzaMeasurementToSize(myPizza) // Medium
pizzaMeasurementToSize("something") // Invalid

trait Crust
case object Classic extends Crust
case object Italian extends Crust
case object Stuffed extends Crust

def findPizzaCost(size: PizzaSize, crust: Crust) : Any = {
  size match {
    case Personal => {
      crust match {
        case Classic => 5.99
        case _ => "Invalid crust"
      }
    }
    case Small => {
      crust match {
        case Stuffed => "Invalid crust"
        case _ => 10.99
      }
    }
    case Medium => {
      crust match {
        case Stuffed => 12.99 + 2.99
        case _ => 12.99
      }
    }
    case Large => {
      crust match {
        case Stuffed => 14.99 + 2.99
        case _ => 14.99
      }
    }
    case Invalid => "Invalid size"
  }
}
findPizzaCost(Personal, Italian) // Invalid crust
findPizzaCost(Large, Stuffed) // 17.98
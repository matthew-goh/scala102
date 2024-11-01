import scala.math._
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

def roundToDP(n: Double, dp: Int): Double = {
  BigDecimal(n).setScale(dp, BigDecimal.RoundingMode.HALF_UP).toDouble
}
//roundToDP(3.4, 2)
//roundToDP(3.525, 2)

//val currentDateTime = LocalDateTime.now()
//val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//currentDateTime.format(formatter)
//currentDateTime.getHour // 12

object Temperature extends Enumeration{
  val Hot, Cold = Value
}

object ItemType extends Enumeration{
  val Food, Drink = Value
}

object ItemGrade extends Enumeration{
  val Standard, Premium = Value
}

sealed trait MenuItem {
  val cost: Double
  val itemType: ItemType.Value
  val temperature: Temperature.Value
  val grade: ItemGrade.Value
}

case object Cola extends MenuItem {
  val cost = 0.50
  val itemType = ItemType.Drink
  val temperature = Temperature.Cold
  val grade = ItemGrade.Standard
}

case object Coffee extends MenuItem {
  val cost = 1.00
  val itemType = ItemType.Drink
  val temperature = Temperature.Hot
  val grade = ItemGrade.Standard
}

case object CheeseSandwich extends MenuItem {
  val cost = 2.00
  val itemType = ItemType.Food
  val temperature = Temperature.Cold
  val grade = ItemGrade.Standard
}

case object SteakSandwich extends MenuItem {
  val cost = 4.50
  val itemType = ItemType.Food
  val temperature = Temperature.Hot
  val grade = ItemGrade.Standard
}

case object Lobster extends MenuItem {
  val cost = 25.00
  val itemType = ItemType.Food
  val temperature = Temperature.Hot
  val grade = ItemGrade.Premium
}

def simpleBill(items: List[MenuItem]): Unit = {
  val itemTotal = items.map(item => item.cost).sum
  println(f"Item total: £$itemTotal%.2f")
}
simpleBill(List(Cola, CheeseSandwich))


// Add support for a service charge:
//When all purchased items are drinks, no service charge is applied
//When purchased items include any food, apply a service charge of 10% to the total bill (rounded to 2 decimal places)
//When purchased items include any hot food, apply a service charge of 20% to the total bill
// with a maximum £20 service charge

// Add support for premium items to menu with a different service charge expectation :
//Lobster, a premium item, is added to the menu at a cost of £25
//When purchased items include any premium food, apply a service charge of 25% to the total bill
// with a maximum £40 service charge

def billWithServiceCharge(items: List[MenuItem]): Unit = {
  val itemTotal: Double = items.map(item => item.cost).sum
  println(f"Item total: £$itemTotal%.2f")

  var serviceCharge: Double = 0.00 // update later
  var totalBill: Double = itemTotal // update later

  val foodItems = items.filter(item => item.itemType == ItemType.Food)
  val premiumFoodItems = items.filter(item => item.grade == ItemGrade.Premium)
  val hotFoodItems = foodItems.filter(item => item.temperature == Temperature.Hot)
//  println(foodItems)
//  println(hotFoodItems)

  foodItems.length match {
    case 0 => { // drinks only: no service charge
      println("Service charge @ 0%: £0.00")
    }
    case _ => { // includes food
      premiumFoodItems.length match{ // includes premium items: 25%, capped at 40
        case numItems if numItems > 0 => {
          serviceCharge = roundToDP(min(itemTotal * 0.25, 40), 2)
          if (serviceCharge == 40) {
            println("Service charge @ 25% (capped at £40): £40.00")
          } else{
            println(f"Service charge @ 25%%: £$serviceCharge%.2f")
          }
        }
        case _ => {
          hotFoodItems.length match { // no premium items
            case 0 => { // no hot food: 10%
              serviceCharge = roundToDP(itemTotal * 0.1, 2)
              println(f"Service charge @ 10%%: £$serviceCharge%.2f")
            }
            case _ => { // includes hot food: 20%, capped at 20
              serviceCharge = roundToDP(min(itemTotal * 0.2, 20), 2)
              if (serviceCharge == 20) {
                println("Service charge @ 20% (capped at £20): £20.00")
              } else{
                println(f"Service charge @ 20%%: £$serviceCharge%.2f")
              }
            }
          }
        }
      }
    }
    totalBill = itemTotal + serviceCharge
  }
  println(f"Total bill: £$totalBill%.2f")
}

billWithServiceCharge(List(Cola, Coffee)) // 1.50
billWithServiceCharge(List(Coffee, CheeseSandwich)) // 3*1.1 = 3.30
billWithServiceCharge(List(Cola, CheeseSandwich, SteakSandwich)) // 7*1.2 = 8.40
billWithServiceCharge(List.fill(30)(SteakSandwich)) // 135 + 20 = 155
billWithServiceCharge(List(Lobster, Cola, Coffee, CheeseSandwich)) // 28.5*1.25 = 35.63
billWithServiceCharge(List.fill(7)(Lobster) ++ List.fill(10)(SteakSandwich)) // 220 + 40 = 260


// Submitting a loyalty card along with the purchased items will reduce the total bill
// (before the service charge is applied) by 2.5% for every star (min: 3, max: 8).
//e.g. A loyalty card with 10 stars will reduce the total bill by 20% (8 stars * 2.5%)
//Loyalty discount is not applied to premium menu items
case class LoyaltyCard(val name: String, var stars: Int = 0)

// Happy hour: drinks are half price between 6pm and 9pm
// Time of transaction; Staff members
// Other currencies
object Currency extends Enumeration {
  val GBP = Value("£")
  val USD = Value("$")
  val EUR = Value("€")
  val CHF = Value("CHF ")
}

// method to return a string with currency symbol + amount
def moneyAmount(currency: Currency.Value, amount: Double): String = {
  f"$currency$amount%.2f"
}
//moneyAmount(Currency.GBP, 8.75)

def fullBill(items: List[MenuItem], card: Option[LoyaltyCard],
             staff: String = "Cashier", currency: Currency.Value = Currency.GBP): Unit = {
  val currentDateTime = LocalDateTime.now()
  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
  println("Transaction: " + currentDateTime.format(formatter))
//  val currentHour = currentDateTime.getHour
  val currentHour = 20
  val happyHour = currentHour >= 18 && currentHour < 21

  val itemTotal: Double = items.map(item => item.cost).sum
  println(s"Item total: ${moneyAmount(currency, itemTotal)}")
  var happyHourReduction: Double = 0 // update later if needed
  var serviceCharge: Double = 0.00 // update later
  var totalBill: Double = itemTotal // update later

  // apply happy hour discount if applicable
  if (happyHour){
    val drinkItems = items.filter(item => item.itemType == ItemType.Drink)
    happyHourReduction = roundToDP(drinkItems.map(item => item.cost).sum * 0.5, 2)
    totalBill -= happyHourReduction
    println(s"Happy hour 50% off drinks: -${moneyAmount(currency, happyHourReduction)}")
    println(s"Adjusted item total: ${moneyAmount(currency, totalBill)}\n")
  }

  val foodItems = items.filter(item => item.itemType == ItemType.Food)
  val premiumFoodItems = items.filter(item => item.grade == ItemGrade.Premium)
  val hotFoodItems = foodItems.filter(item => item.temperature == Temperature.Hot)
  val standardItems = items.filter(item => item.grade == ItemGrade.Standard)

  // apply loyalty discount if customer has a card
  var discountAmount: Double = 0
  card match {
    case Some(LoyaltyCard(name, stars)) => {
      println(s"Loyalty card: $name")
      println(s"Loyalty stars: $stars")
      if (stars >= 3) {
        val discountPercentage = min(2.5*stars, 20)
        // if happy hour, apply discount to half priced drinks
        if (happyHour){
          val newPriceList = standardItems.map{
            item => if (item.itemType == ItemType.Drink) item.cost * 0.5 else item.cost
          }
          discountAmount = roundToDP(newPriceList.sum * discountPercentage / 100, 2)
        } else{
          discountAmount = roundToDP(standardItems.map(item => item.cost).sum * discountPercentage / 100, 2)
        }

        if (stars % 2 == 0 || stars > 8){ // discountPercentage is an integer
          println(f"${discountPercentage}%.0f%% loyalty discount on standard items: ${moneyAmount(currency, discountAmount)}")
        } else { // discountPercentage is a .5
          println(f"${discountPercentage}%% loyalty discount on standard items: -${moneyAmount(currency, discountAmount)}")
        }
        totalBill -= discountAmount

      } else {
        println(s"Loyalty discount: -${currency}0.00")
      }
      println(s"Subtotal: ${moneyAmount(currency, totalBill)}\n")
    }
    case None => println("Loyalty card: None\n")
  }

  // calculate service charge
  foodItems.length match {
    case 0 => { // drinks only: no service charge
      println(s"Service charge @ 0%: ${currency}0.00")
    }
    case _ => { // includes food
      premiumFoodItems.length match{ // includes premium items: 25%, capped at 40
        case x if x > 0 => {
          serviceCharge = roundToDP(min(totalBill * 0.25, 40), 2)
          if (serviceCharge == 40) {
            println(s"Service charge @ 25% (capped at ${currency}40): ${currency}40.00")
          } else{
            println(s"Service charge @ 25%: ${moneyAmount(currency, serviceCharge)}")
          }
        }
        case _ => {
          hotFoodItems.length match { // no premium items
            case 0 => { // no hot food: 10%
              serviceCharge = roundToDP(totalBill * 0.1, 2)
              println(s"Service charge @ 10%: ${moneyAmount(currency, serviceCharge)}")
            }
            case _ => { // includes hot food: 20%, capped at 20
              serviceCharge = roundToDP(min(totalBill * 0.2, 20), 2)
              if (serviceCharge == 20) {
                println(s"Service charge @ 20% (capped at ${currency}20): ${currency}20.00")
              } else{
                println(s"Service charge @ 20%: ${moneyAmount(currency, serviceCharge)}")
              }
            }
          }
        }
      }
    }
  }

  val totalToPay = totalBill + serviceCharge
  println(s"Total to pay: ${moneyAmount(currency, totalToPay)}\n")
  println(s"You have been served by: $staff")
}

val currentStaff = "Mary"
fullBill(List(Coffee, Coffee, Cola), Some(LoyaltyCard("John Smith", 6)))
fullBill(List(Cola, CheeseSandwich, SteakSandwich), None, currency = Currency.EUR)
fullBill(List(Cola, CheeseSandwich, SteakSandwich),
  Some(LoyaltyCard("John Smith", 3)), currency = Currency.USD)
fullBill(List(Lobster, Cola, Coffee, CheeseSandwich),
  Some(LoyaltyCard("John Smith", 7)), currentStaff, Currency.CHF)
fullBill(List.fill(7)(Lobster) ++ List.fill(10)(SteakSandwich) ++ List.fill(10)(Coffee),
  Some(LoyaltyCard("John Smith", 9)), currentStaff)

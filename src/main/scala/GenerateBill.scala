import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.math._

object GenerateBill extends App {
  val currentStaff = "Mary"

  def roundToDP(n: Double, dp: Int): Double = {
    BigDecimal(n).setScale(dp, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  // obtain a string with currency symbol + amount
  def moneyAmount(currency: Currency.Value, amount: Double): String = {
    f"$currency$amount%.2f"
  }

  def fullBill(items: List[MenuItem], card: Option[LoyaltyCard],
               staff: String = "Cashier", currency: Currency.Value = Currency.GBP): Unit = {
    // get date and time; check if it is happy hour
    val currentDateTime = LocalDateTime.now()
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    println("Transaction: " + currentDateTime.format(formatter))
    //  val currentHour = currentDateTime.getHour
    val currentHour = 20
    val happyHour: Boolean = currentHour >= 18 && currentHour < 21

    val itemTotal: Double = items.map(item => item.cost).sum
    println(s"Item total: ${moneyAmount(currency, itemTotal)}")


    // apply happy hour discount if applicable
    val drinkItems = items.filter(item => item.itemType == ItemType.Drink)
    val happyHourReduction = if (happyHour) roundToDP(drinkItems.map(item => item.cost).sum * 0.5, 2) else 0
    val totalAfterHappyHour = itemTotal - happyHourReduction

    if (happyHour){
      println(s"Happy hour 50% off drinks: -${moneyAmount(currency, happyHourReduction)}")
      println(s"Adjusted item total: ${moneyAmount(currency, totalAfterHappyHour)}\n")
    }

//    val foodItems = items.filter(item => item.itemType == ItemType.Food)
//    val premiumFoodItems = items.filter(item => item.grade == ItemGrade.Premium)
//    val hotFoodItems = foodItems.filter(item => item.temperature == Temperature.Hot)
//    val standardItems = items.filter(item => item.grade == ItemGrade.Standard)

    // apply loyalty discount if customer has a card
    def calculateLoyaltyDiscount(items: List[MenuItem], card: Option[LoyaltyCard], happyHour: Boolean, currentTotal: Double,
                         currency: Currency.Value): Double = {
      card match {
        case Some(LoyaltyCard(name, stars)) => {
          println(s"Loyalty card: $name")
          println(s"Loyalty stars: $stars")
          if (stars >= 3) {
            val standardItems = items.filter(item => item.grade == ItemGrade.Standard)
            val discountPercentage = min(2.5*stars, 20)

            // if happy hour, discount is applied to half-priced drinks
            val newPriceList: List[Double] =
              if (!happyHour) standardItems.map(item => item.cost)
              else standardItems.map{
                item => if (item.itemType == ItemType.Drink) item.cost * 0.5 else item.cost
              }
            val discountAmount = roundToDP(newPriceList.sum * discountPercentage / 100, 2)

            if (stars % 2 == 0 || stars > 8){ // discountPercentage is an integer
              println(f"${discountPercentage}%.0f%% loyalty discount on standard items: ${moneyAmount(currency, discountAmount)}")
            } else { // discountPercentage is a .5
              println(f"${discountPercentage}%% loyalty discount on standard items: -${moneyAmount(currency, discountAmount)}")
            }

            println(s"Subtotal: ${moneyAmount(currency, currentTotal - discountAmount)}\n")
            discountAmount // return value
          }
          else { // stars < 3
            println(s"Loyalty discount: -${currency}0.00")
            println(s"Subtotal: ${moneyAmount(currency, currentTotal)}\n")
            0 // return value
          }
        }
        case None => {
          println("Loyalty card: None\n")
          0 // return value
        }
      }
    }

    // calling the function will also print all the required info
    val discountAmount = calculateLoyaltyDiscount(items, card, happyHour, currentTotal = totalAfterHappyHour, currency)
    val totalAfterLoyalty = totalAfterHappyHour - discountAmount


    // calculate service charge
    def calculateServiceCharge(items: List[MenuItem], currentTotal: Double, currency: Currency.Value): Double = {
      val foodItems = items.filter(item => item.itemType == ItemType.Food)
      val premiumFoodItems = items.filter(item => item.grade == ItemGrade.Premium)
      val hotFoodItems = foodItems.filter(item => item.temperature == Temperature.Hot)

      foodItems.length match {
        case 0 => { // drinks only: no service charge
          println(s"Service charge @ 0%: ${currency}0.00")
          0 // return value
        }
        case _ => { // includes food
          premiumFoodItems.length match{
            case numItems if numItems > 0 => { // includes premium items: 25%, capped at 40
              val serviceCharge = roundToDP(min(currentTotal * 0.25, 40), 2)
              if (serviceCharge == 40) {
                println(s"Service charge @ 25% (capped at ${currency}40): ${currency}40.00")
              } else {
                println(s"Service charge @ 25%: ${moneyAmount(currency, serviceCharge)}")
              }
              serviceCharge // return value
            }
            case _ => { // no premium items
              hotFoodItems.length match {
                case 0 => { // no hot food: 10%
                  val serviceCharge = roundToDP(currentTotal * 0.1, 2)
                  println(s"Service charge @ 10%: ${moneyAmount(currency, serviceCharge)}")
                  serviceCharge // return value
                }
                case _ => { // includes hot food: 20%, capped at 20
                  val serviceCharge = roundToDP(min(currentTotal * 0.2, 20), 2)
                  if (serviceCharge == 20) {
                    println(s"Service charge @ 20% (capped at ${currency}20): ${currency}20.00")
                  } else {
                    println(s"Service charge @ 20%: ${moneyAmount(currency, serviceCharge)}")
                  }
                  serviceCharge // return value
                }
              }
            }
          }
        }
      }
    }

    val serviceCharge = calculateServiceCharge(items, currentTotal = totalAfterLoyalty, currency)
    val totalToPay = totalAfterLoyalty + serviceCharge
    println(s"Total to pay: ${moneyAmount(currency, totalToPay)}\n")
    println(s"You have been served by: $staff")
  }

  // For testing
  fullBill(List(Coffee, Coffee, Cola), Some(LoyaltyCard("John Smith", 6)))
  fullBill(List(Cola, CheeseSandwich, SteakSandwich), None, currency = Currency.EUR)
  fullBill(List(Cola, CheeseSandwich, SteakSandwich),
    Some(LoyaltyCard("John Smith", 3)), currency = Currency.USD)
  fullBill(List(Lobster, Cola, Coffee, CheeseSandwich),
    Some(LoyaltyCard("John Smith", 7)), currentStaff, Currency.CHF)
  fullBill(List.fill(7)(Lobster) ++ List.fill(10)(SteakSandwich) ++ List.fill(10)(Coffee),
    Some(LoyaltyCard("John Smith", 9)), currentStaff)
}

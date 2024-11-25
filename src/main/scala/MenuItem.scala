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

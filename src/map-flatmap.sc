// The map and flatMap functions support case matching
List(1, 2, 3).map(_ * 2)
List(1, 2, 3).map{
  case 1 => 10
  case x => x * 2
} // List(10, 4, 6)

// The Map collection provides a tuple as the input for the map function
// and can be mapped to a Seq or a Map if the output is a tuple or a single value
Map("myKey" -> "myValue").map {
  case (key, value) => s"key: $key, value: $value"
} // List(key: myKey, value: myValue)

Map("myKey" -> "myValue", "k2" -> "v2").map {
  case (key, value) => value -> key
} // Map(myValue -> myKey, v2 -> k2)

// Create nested collections
val x = List(1, 2, 3).map(x => List(x*2, x*4, x*6))
// List[List[Int]] = List(List(2, 4, 6), List(4, 8, 12), List(6, 12, 18))
x.flatten // transform into a single collection
// List(2, 4, 6, 4, 8, 12, 6, 12, 18)

// flatMap does map, then flatten. Output must be a container
List(1,2,3).flatMap(x => List(x*2, x*4, x*6))

// flatten part extracts values from Options and removes None entries
List(1,2,3).map {
  case x@(1 | 3) => Some(x * 2)
  case _ => None
} // List(Some(2), None, Some(6))
List(1,2,3).flatMap {
  case x@(1 | 3) => Some(x * 2)
  case _ => None
} // List(2, 6)

// Mapping on an option
Some(3).map{
  case 1 => 10
  case x => x * 2
} // Some(6)
Option.empty[Int].map(n => n * 3) // None

// flatMap is used when the return value is itself an option
Some(3).map(x => if(x == 3) Some(9) else None) // Some(Some(9))
Some(3).flatMap(x => if(x == 3) Some(9) else None) // Some(9)
Some(2).flatMap(x => if(x == 3) Some(9) else None) // None

//////////////
// map on eithers: perform function on Right or just return Left
Right[String, Int](3).map(number => number * 3) // Right (9)
Left[String, Int]("hi").map(number => number * 3) // Left("hi")

// flatMap is used when the return value is itself an either
Right[String, Int](3).flatMap(x => if(x == 3) Right[String, Int](9) else Left[String, Int]("false"))
// Right(9)
Right[String, Int](2).flatMap(x => if(x == 3) Right[String, Int](9) else Left[String, Int]("false"))
// Left("false")
Left[String, Int]("hi").flatMap(x => if(x == 3) Right[String, Int](9) else Left[String, Int]("false"))
// Left("hi")
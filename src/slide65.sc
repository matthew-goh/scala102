// Write a function using map which :-
//Takes a String “macbooks”
//Maps on each char to make them uppercase, and returns “MACBOOKS ARE THE BEST"
def macbooksMap(s: String = "macbooks"): String = {
  s.map(ch => ch.toUpper) + " ARE THE BEST"
}
macbooksMap()

// Write a function which :-
//Turns a list of strings to ints, e.g. List(“1”, “2”, “3”) -> List(1,2,3)
//Multiplies each int by 2
//Finds the sum of the List
def sumStringList(L: List[String]): Int = {
  //  L.map(s => s.toInt * 2).sum
  try {
    L.map(s => s.toInt * 2).sum
  } catch {
    case e: Exception => {
      println("List element cannot be converted to Int")
      0
    }
  }
}
sumStringList(List("65", "3", "-8")) // 120
sumStringList(List("65.7", "3", "-8")) // Exception
sumStringList(List("abc", "3", "-8")) // Exception

// Write a function that accepts an optional Int and multiplies the integer by 12,
// if nothing is supplied return 12
def mult12(num: Option[Int]): Int = {
  num match {
    case Some(n) => n * 12
    case None => 12
  }
}
mult12(Some(-2))
mult12(None)

// The following value is created to model recent exam results.
// A None means that the student did not attend.
val scores = Seq(None, Some("A"), Some("B"), Some("C"), None, Some("F"))
// What keyword could be used to only produce a list of results of those students that attended?
scores.flatten // List(A, B, C, F)
// Using map, turn the scores of the students that didn’t attend into an F.
scores.map{
  case Some(grade) => grade
  case None => "F"
} // List(F, A, B, C, F, F)

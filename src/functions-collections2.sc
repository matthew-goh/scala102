import scala.collection.immutable.Queue

// Default parameters
def greeting(greeting: String = "Hi",
             name: String = "Mac",
             punctuation: String = "!"): String = {
  greeting + " " + name + punctuation
}

val a = greeting("Hi", "Mac", "!")   // "Hi Mac!"
val b = greeting() 	  			    // "Hi Mac!"
val c = greeting("Hello") 		    // "Hello Mac!"
val d = greeting("Mornin", "Angle")  // "Mornin Angle!"
// if parameters are not named, they follow the order in the function definition
val e = greeting(",") 		  	    // ", Mac!"
val f = greeting(punctuation = ",") 	// "Hi Mac,"

// Higher order functions
// accepts a function as a parameter and/or returns a function as a result
val add: (Int, Int) => Int = (a,b) => a + b
val subtract: (Int, Int) => Int = (a,b) => a - b
val multiply: (Int, Int) => Int = (a,b) => a * b
// function type is (Int , Int) => Int
def calculate(func: (Int , Int) => Int, val1: Int, val2: Int): Int = func(val1, val2)
calculate(add, 1, 2)

/////////////////////
// List: allows quick operation on the head and tail of the collection
List(0, 1, 2, 3, 4, 5)
// Stream: lazy version of a List - elements are evaluated only when they are needed
Stream(0, 1, 2, 3, 4, 5)
LazyList(0, 1, 2, 3, 4, 5)
Queue(1, 2, 3, 4, 5, 6, 7, 8) // enter at the back, exit at the front

val setOne = Set(1, 2, 2, 3, 4, 5, 5) // HashSet(5, 1, 2, 3, 4)

Map(("x", 24), ("y", 25), ("z", 26)) // create a Map using tuples
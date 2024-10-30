// embed variable references directly into a processed string literal

// s interpolator
val name = "Billy"
println(s"My name is $name") // reference variables or arbitrary expressions with $
println(s"5 * 5 = ${5 * 5}") // 5 * 5 = 25
val number = 10
println(s"4 * $number = ${4 * number}") // 4 * 10 = 40

// f interpolator
val pi: Double = 3.141592653589793
println(f"Pi to 2 decimal places is $pi%.2f") // print pi to 2dp
// %f for float, $d for decimal, %i for integer

// raw interpolator: similar to s but does not escape literals in the string
println(s"I'm going to go on a \nnew line")
println(raw"I'm going to go on a \nnew line")

/////////////////////
// Slide 17 exercises
val name1 = "Kev"
val name2 = "Amy"
println(s"$name1 is 10 years older than $name2")

val age1 = 32
val age2 = 22
println(s"$name1 is ${age1 - age2} years older than $name2")

val age1d: Double = 32.5
val age2d: Double = 22.5
println(f"$name1 is ${age1d - age2d}%.0f years older than $name2")

// Slide 18 exercises
//Use string interpolation to avoid the new lines from this string. "\n\n\n\n\n\n\n\nsameline\n\n"
println(raw"\n\n\n\n\n\n\n\nsameline\n\n")

// Using string interpolation print out the person's details. Try to use all 3 interpolators.
case class Person(firstName: String, surname: String, age: Int, favouriteRegex: String, avgFirstServePct: Double)
val p1 = Person("Kelly", "Brown", 31, "^[\\\\]{5,10}$", 65.7629)
println(s"Name: ${p1.firstName} ${p1.surname}")
println(f"Age: ${p1.age}")
println(raw"Favourite Regex: ${p1.favouriteRegex}")
println(f"First serve %%: ${p1.avgFirstServePct}%.1f%%")

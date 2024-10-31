//Either:-
//  takes two type parameters (one for Left and one for Right)
//is popularly used for error handling where Left is usually the error case
//Is a good alternative to using a Try if you would like to return more than just a Throwable when there is an error

// Create an Either by specifying the type parameters and creating a Right or Left
def getIntAndAdd1(s: String): Either[String, Int] = {
  try {
    Right(s.toInt)
  } catch {
    case e: Exception => Left("Failed")
  }
}
getIntAndAdd1("4") // Either[String,Int] = Right(4)
getIntAndAdd1("f") // Either[String,Int] = Left(Failed)

getIntAndAdd1("12").right.map(num => num + 1) // Either is now right-biased
getIntAndAdd1("12").map(num => num + 1) // Right(13)
getIntAndAdd1("hello").left.map(word => word.toUpperCase) // Left(FAILED)
// map is only performed if the projection is correct
// here, .right.map is called on a .left
getIntAndAdd1("hello").map(num => num + 1) // Left(Failed)

// Can use pattern matching on a Left or Right
getIntAndAdd1("12") match {
  case Left(_) => println("We have a Left")
  case Right(_) => println("We have a Right")
}

////////////////////////
// Slide 56
//Write a function which :-
//1.takes a username and password of type String
//2.calls userExists
//3.returns an Either with Left of type UserNotFoundError or Right of type Boolean
def userExists(username: String, password: String): Boolean = {
  (username, password) match {
    case ("Boris Johnson", "pw123") => true
    case("Barack Obama", "merica") => true
    case _ => false
  }
}

case class UserNotFoundError(message: String = "User not found")
def userExistsEither(username: String, password: String): Either[UserNotFoundError, Boolean] = {
  if (userExists(username, password)){Right(true)}
  else{Left(UserNotFoundError())}
}
userExistsEither("Boris Johnson", "pw123")
userExistsEither("aa", "bbb")

// Slide 57
object EitherStyle{
  def parse(s: String): Either[NumberFormatException, Int] = {
    if (s.matches("-?[0-9]+")) Right(s.toInt)
    else Left(new NumberFormatException(s"$s is not a valid integer."))
  }
}
EitherStyle.parse("23").isRight // true
EitherStyle.parse("twenty").isLeft // true
//EitherStyle.parse(100).isLeft // wrong input type
EitherStyle.parse(100.toString).isLeft // false
EitherStyle.parse("21.5").isRight // false

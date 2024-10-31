sealed trait Temperature // sealed: can only be extended from within the same file
case object Cold extends Temperature
case object Hot extends Temperature
// extending a trait implements its members (functions, values)

val weather: Temperature = Cold
val res = weather match {
  case Cold => 5
  case Hot => 10
  case _ => 0
} // res = 5

// When matching on objects, you can also extract their members and use them in the return value
abstract class Notification
case class Email(sender: String, title: String, body: String) extends Notification
case class SMS(caller: String, message: String) extends Notification
case class VoiceRecording(contactName: String, link: String) extends Notification

val notification: Notification = SMS("12345", "Are you there?")

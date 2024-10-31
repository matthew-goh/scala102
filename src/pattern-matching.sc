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
notification match {
  case Email(email, title, _) => "Email from "+email+" with title: "+title
  case SMS(number, message) => "SMS from "+number+"! Message: "+message
  case VoiceRecording(name, link) => "Voice Recording from "+name+" - "+link
} // SMS from 12345! Message: Are you there?

// Adding guards (extra checks) to a pattern match
val spamFilter = List("email1@example.com")
notification match {
  case Email(email, title, _) if title.contains("Jira") =>
    "Probably a pull request"
  case Email(email, title, _) if spamFilter.contains(email)  =>
    "Delete"
  case Email(email, title, _) if title.contains("Jira") =>
    "Email from "+email+" with title: "+title
  case _ => "Ignored"
}

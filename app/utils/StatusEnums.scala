package app.utils



object StatusEnums  extends Enumeration {
  type StatusEnums = Value
  val ACTIVE = Value("ACTIVE")
  val PENDING = Value("PENDING")
  val ARCHIVED = Value("ARCHIVED")
}

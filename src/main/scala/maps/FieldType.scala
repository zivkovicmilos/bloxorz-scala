package maps

// Defines the different types of fields
object FieldType extends Enumeration {
  type FieldType = Value

  val
  VOID, // -
  START, // S
  TARGET, // T
  SPECIAL, // .
  GROUND, // o
  BLOCK // X
  = Value

  def getValue(fieldType: FieldType): String = {
    fieldType match {
      case VOID => "-"
      case START => "S"
      case TARGET => "T"
      case SPECIAL => "."
      case GROUND => "o"
      case BLOCK => "x"
      case _ => " "
    }
  }
}

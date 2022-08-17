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

  def getType(field: Char): FieldType = {
    field match {
      case '-' => VOID
      case 'S' => START
      case 'T' => TARGET
      case '.' => SPECIAL
      case 'o' => GROUND
      case 'x' => BLOCK
      case _ => VOID
    }
  }

  def getColor(fieldType: FieldType): String = {
    fieldType match {
      case VOID => Console.WHITE
      case START => Console.CYAN
      case TARGET => Console.RED
      case SPECIAL => Console.YELLOW
      case GROUND => Console.GREEN
      case BLOCK => Console.MAGENTA
      case _ => Console.RESET
    }
  }
}

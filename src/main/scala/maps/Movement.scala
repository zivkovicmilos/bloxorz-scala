package maps

// Defines the different types of player movements
object Movement extends Enumeration {
  type Movement = Value

  val
  UP, // u
  DOWN, // d
  LEFT, // l
  RIGHT // r
  = Value

  def getMovement(move: Char): Movement = {
    move match {
      case 'u' => UP
      case 'd' => DOWN
      case 'l' => LEFT
      case 'r' => RIGHT
      case _ => throw new Error("Unknown move")
    }
  }

  def getChar(movement: Movement): Char = {
    movement match {
      case UP => 'u'
      case DOWN => 'd'
      case LEFT => 'l'
      case RIGHT => 'r'
      case _ => throw new Error("Unknown move")
    }
  }
}

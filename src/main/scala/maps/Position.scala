package maps

import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

// X -> Horizontal position (columns); ascending right
// Y -> Vertical position (rows); ascending down
case class Position(var x: Int, var y: Int) {
  def move(move: Movement): Unit = {
    move match {
      case UP => moveUp()
      case DOWN => moveDown()
      case LEFT => moveLeft()
      case RIGHT => moveRight()
      case _ =>
    }
  }

  private def moveUp(): Unit = {
    y -= 1
  }

  private def moveDown(): Unit = {
    y += 1
  }

  private def moveLeft(): Unit = {
    x -= 1
  }

  private def moveRight(): Unit = {
    x += 1
  }
}

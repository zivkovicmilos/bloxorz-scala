package maps

import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

// X -> Horizontal position (columns); ascending right
// Y -> Vertical position (rows); ascending down
case class Position(x: Int, y: Int) {
  def move(move: Movement): Position = {
    move match {
      case UP => moveUp()
      case DOWN => moveDown()
      case LEFT => moveLeft()
      case RIGHT => moveRight()
    }
  }

  private def moveUp(): Position = {
    Position(x, y - 1)
  }

  private def moveDown(): Position = {
    Position(x, y + 1)
  }

  private def moveLeft(): Position = {
    Position(x - 1, y)
  }

  private def moveRight(): Position = {
    Position(x + 1, y)
  }

  def isSameColumn(position: Position): Boolean = {
    x == position.x
  }

  def isSameRow(position: Position): Boolean = {
    y == position.y
  }

  def isLeftTo(position: Position): Boolean = {
    !isRightTo(position)
  }

  def isRightTo(position: Position): Boolean = {
    x > position.x
  }

  def isDownTo(position: Position): Boolean = {
    !isUpTo(position)
  }

  def isUpTo(position: Position): Boolean = {
    y < position.y
  }

  def columnDistance(position: Position): Int = {
    Math.abs(x - position.x)
  }

  def rowDistance(position: Position): Int = {
    Math.abs(y - position.y)
  }
}

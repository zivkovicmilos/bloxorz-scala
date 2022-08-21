package maps

import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

// X -> Horizontal position (columns); ascending right
// Y -> Vertical position (rows); ascending down
case class PositionAlt(x: Int, y: Int) {
  def move(move: Movement): PositionAlt = {
    move match {
      case UP => moveUp()
      case DOWN => moveDown()
      case LEFT => moveLeft()
      case RIGHT => moveRight()
    }
  }

  private def moveUp(): PositionAlt = {
    PositionAlt(x, y - 1)
  }

  private def moveDown(): PositionAlt = {
    PositionAlt(x, y + 1)
  }

  private def moveLeft(): PositionAlt = {
    PositionAlt(x - 1, y)
  }

  private def moveRight(): PositionAlt = {
    PositionAlt(x + 1, y)
  }

  def isSameColumn(position: PositionAlt): Boolean = {
    x == position.x
  }

  def isSameRow(position: PositionAlt): Boolean = {
    y == position.y
  }

  def isLeftTo(position: PositionAlt): Boolean = {
    !isRightTo(position)
  }

  def isRightTo(position: PositionAlt): Boolean = {
    x > position.x
  }

  def isDownTo(position: PositionAlt): Boolean = {
    !isUpTo(position)
  }

  def isUpTo(position: PositionAlt): Boolean = {
    y < position.y
  }

  def columnDistance(position: PositionAlt): Int = {
    Math.abs(x - position.x)
  }

  def rowDistance(position: PositionAlt): Int = {
    Math.abs(y - position.y)
  }
}

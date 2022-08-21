package maps

import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

case class Player(a: Position, b: Position) {
  def move(movement: Movement): Player = {
    movement match {
      case UP => moveUp()
      case DOWN => moveDown()
      case LEFT => moveLeft()
      case RIGHT => moveRight()
    }
  }

  def moveUp(): Player = {
    if (isUpright) {
      // ab => b up a
      return Player(a.move(UP), b).roll(UP)
    }

    // if a and b are in the same row,
    // move both up
    if (a.isSameRow(b)) {
      return roll(UP)
    }

    // if a and b are in the same column,
    // move the one with the highest row (most down) up
    if (a.isDownTo(b)) {
      return Player(a.move(UP), b).roll(UP)
    }

    Player(a, b.move(UP)).roll(UP)
  }

  def isUpright: Boolean = {
    a == b
  }

  private def roll(direction: Movement): Player = {
    direction match {
      case UP => Player(a.move(UP), b.move(UP))
      case DOWN => Player(a.move(DOWN), b.move(DOWN))
      case LEFT => Player(a.move(LEFT), b.move(LEFT))
      case RIGHT => Player(a.move(RIGHT), b.move(RIGHT))
    }
  }

  def moveDown(): Player = {
    if (isUpright) {
      // ab => b down a
      return Player(a.move(DOWN), b).roll(DOWN)
    }

    // if a and b are in the same row,
    // move both down
    if (a.isSameRow(b)) {
      return roll(DOWN)
    }

    // if a and b are in the same column,
    // move the one with the lowest row (most up) down
    if (a.isUpTo(b)) {
      return Player(a.move(DOWN), b).roll(DOWN)
    }

    Player(a, b.move(DOWN)).roll(DOWN)
  }

  def moveLeft(): Player = {
    if (isUpright) {
      // ab => a left b
      return Player(a.move(LEFT), b).roll(LEFT)
    }

    // if a and b are in the same column,
    // move both left
    if (a.isSameColumn(b)) {
      return roll(LEFT)
    }

    // if a and b are in the same row,
    // move the one that is rightest to the left
    if (a.isRightTo(b)) {
      return Player(a.move(LEFT), b).roll(LEFT)
    }

    Player(a, b.move(LEFT)).roll(LEFT)
  }

  def moveRight(): Player = {
    if (isUpright) {
      // ab => b right a
      return Player(a.move(RIGHT), b).roll(RIGHT)
    }

    // if a and b are in the same column,
    // move both right
    if (a.isSameColumn(b)) {
      return roll(RIGHT)
    }

    // if a and b are in the same row,
    // move the one that is leftest to the right
    if (a.isLeftTo(b)) {
      return Player(a.move(RIGHT), b).roll(RIGHT)
    }

    Player(a, b.move(RIGHT)).roll(RIGHT)
  }
}

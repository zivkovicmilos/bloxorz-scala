package maps

import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

case class PlayerAlt(a: PositionAlt, b: PositionAlt) {
  def move(movement: Movement): PlayerAlt = {
    movement match {
      case UP => moveUp()
      case DOWN => moveDown()
      case LEFT => moveLeft()
      case RIGHT => moveRight()
    }
  }

  def moveUp(): PlayerAlt = {
    if (isUpright) {
      // ab => b up a
      return PlayerAlt(a.move(UP), b).roll(UP)
    }

    // if a and b are in the same row,
    // move both up
    if (a.isSameRow(b)) {
      return roll(UP)
    }

    // if a and b are in the same column,
    // move the one with the highest row (most down) up
    if (a.isDownTo(b)) {
      return PlayerAlt(a.move(UP), b).roll(UP)
    }

    PlayerAlt(a, b.move(UP)).roll(UP)
  }

  def moveDown(): PlayerAlt = {
    if (isUpright) {
      // ab => b down a
      return PlayerAlt(a.move(DOWN), b).roll(DOWN)
    }

    // if a and b are in the same row,
    // move both down
    if (a.isSameRow(b)) {
      return roll(DOWN)
    }

    // if a and b are in the same column,
    // move the one with the lowest row (most up) down
    if (a.isUpTo(b)) {
      return PlayerAlt(a.move(DOWN), b).roll(DOWN)
    }

    PlayerAlt(a, b.move(DOWN)).roll(DOWN)
  }

  def moveLeft(): PlayerAlt = {
    if (isUpright) {
      // ab => a left b
      return PlayerAlt(a.move(LEFT), b).roll(LEFT)
    }

    // if a and b are in the same column,
    // move both left
    if (a.isSameColumn(b)) {
      return roll(LEFT)
    }

    // if a and b are in the same row,
    // move the one that is rightest to the left
    if (a.isRightTo(b)) {
      return PlayerAlt(a.move(LEFT), b).roll(LEFT)
    }

    PlayerAlt(a, b.move(LEFT)).roll(LEFT)
  }

  def moveRight(): PlayerAlt = {
    if (isUpright) {
      // ab => b right a
      return PlayerAlt(a.move(RIGHT), b).roll(RIGHT)
    }

    // if a and b are in the same column,
    // move both right
    if (a.isSameColumn(b)) {
      return roll(RIGHT)
    }

    // if a and b are in the same row,
    // move the one that is leftest to the right
    if (a.isLeftTo(b)) {
      return PlayerAlt(a.move(RIGHT), b).roll(RIGHT)
    }

    PlayerAlt(a, b.move(RIGHT)).roll(RIGHT)
  }

  def isUpright: Boolean = {
    a == b
  }

  private def roll(direction: Movement): PlayerAlt = {
    direction match {
      case UP => PlayerAlt(a.move(UP), b.move(UP))
      case DOWN => PlayerAlt(a.move(DOWN), b.move(DOWN))
      case LEFT => PlayerAlt(a.move(LEFT), b.move(LEFT))
      case RIGHT => PlayerAlt(a.move(RIGHT), b.move(RIGHT))
    }
  }
}

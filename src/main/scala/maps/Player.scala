package maps

import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

class Player(position: Position) {
  var a: Position = position.copy()
  var b: Position = position.copy()

  def isUpright: Boolean = {
    a == b
  }

  def move(movement: Movement): Unit = {
    movement match {
      case UP => moveUp()
      case DOWN => moveDown()
      case LEFT => moveLeft()
      case RIGHT => moveRight()
      case _ =>
    }
  }

  private def rollUp(): Unit = {
    a.move(UP)
    b.move(UP)
  }

  private def rollDown(): Unit = {
    a.move(DOWN)
    b.move(DOWN)
  }

  private def rollLeft(): Unit = {
    a.move(LEFT)
    b.move(LEFT)
  }

  private def rollRight(): Unit = {
    a.move(RIGHT)
    b.move(RIGHT)
  }

  def moveUp(): Unit = {
    if (isUpright) {
      // ab => b up a
      a.move(UP)

      rollUp()

      return
    }

    // if a and b are in the same row,
    // move both up
    if (a.isSameRow(b)) {
      rollUp()

      return
    }

    // if a and b are in the same column,
    // move the one with the highest row (most down) up
    if (a.isDownTo(b)) {
      a.move(UP)
    } else {
      b.move(UP)
    }

    rollUp()
  }

  def moveDown(): Unit = {
    if (isUpright) {
      // ab => b down a
      a.move(DOWN)

      rollDown()

      return
    }

    // if a and b are in the same row,
    // move both down
    if (a.isSameRow(b)) {
      rollDown()

      return
    }

    // if a and b are in the same column,
    // move the one with the lowest row (most up) down
    if (a.isUpTo(b)) {
      a.move(DOWN)
    } else {
      b.move(DOWN)
    }

    rollDown()
  }

  def moveLeft(): Unit = {
    if (isUpright) {
      // ab => a left b
      a.move(LEFT)

      rollLeft()

      return
    }

    // if a and b are in the same column,
    // move both left
    if (a.isSameColumn(b)) {
      rollLeft()

      return
    }

    // if a and b are in the same row,
    // move the one that is rightest to the left
    if (a.isRightTo(b)) {
      a.move(LEFT)
    } else {
      b.move(LEFT)
    }

    rollLeft()
  }

  def moveRight(): Unit = {
    if (isUpright) {
      // ab => b right a
      a.move(RIGHT)

      rollRight()

      return
    }

    // if a and b are in the same column,
    // move both right
    if (a.isSameColumn(b)) {
      rollRight()

      return
    }

    // if a and b are in the same row,
    // move the one that is leftest to the right
    if (a.isLeftTo(b)) {
      a.move(RIGHT)
    } else {
      b.move(RIGHT)
    }

    rollRight()
  }

  // TODO remove
  def copy(): Player = {
    val p = new Player(Position(0, 0))
    p.a = a.copy()
    p.b = b.copy()

    p
  }
}

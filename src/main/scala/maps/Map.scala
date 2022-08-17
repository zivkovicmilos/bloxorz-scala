package maps

import game.GameStatus.{FAILURE, GameStatus, ONGOING, SUCCESS}
import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

class Map(map: Array[Array[BoardField]]) {
  var playerPosition: Position = Position(0, 0)
  var coveredField: BoardField = BoardField(FieldType.START, 0, 0)

  def drawMap(): Unit = {
    MapDrawer.drawMap(map)
  }

  def initializePlayer(): Unit = {
    println(f"Map dimensions are y: ${map.length} x: ${map(0).length}")

    for {
      y <- map.indices
    } {
      for {
        x <- map(y).indices
      } {
        if (map(y)(x).fieldType == FieldType.START) {
          playerPosition = Position(x, y)
          coveredField = BoardField(FieldType.START, x, y)
          map(y)(x) = BoardField(FieldType.BLOCK, x, y)

          return
        }
      }
    }

    // TODO move this check to the map loader
    throw new Error("No start position")
  }

  def movePlayer(move: Movement): Unit = {
    if (isAllowedMove(move)) {
      val prevX = playerPosition.x
      val prevY = playerPosition.y

      println(f"Previous positions $prevX $prevY")

      playerPosition.move(move)

      val x = playerPosition.x
      val y = playerPosition.y

      println(f"New positions $x $y")


      map(prevY)(prevX) = coveredField
      coveredField = map(y)(x)
      map(y)(x) = BoardField(FieldType.BLOCK, x, y)
    }
  }

  private def isAllowedMove(move: Movement): Boolean = {
    move match {
      case UP => playerPosition.y > 0
      case DOWN => playerPosition.y + 1 < map.length
      case LEFT => playerPosition.x > 0
      case RIGHT => playerPosition.x + 1 < map(0).length
    }
  }

  def getGameStatus: GameStatus = {
    // Check if the target has been reached
    if (coveredField.fieldType == FieldType.TARGET) {
      return SUCCESS
    }

    // Check if the player is out of bounds
    if (coveredField.fieldType == FieldType.VOID) {
      return FAILURE
    }

    // Check if the player is on a special tile
    if (coveredField.fieldType == FieldType.SPECIAL) {
      return FAILURE
    }

    ONGOING
  }
}

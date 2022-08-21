package maps

import game.GameStatus.{FAILURE, GameStatus, ONGOING, SUCCESS}
import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

class Map(map: Array[Array[BoardField]], player: Player) {
  // Shorthand for drawing the map
  def drawMap(): Unit = {
    MapDrawer.drawMapWithPlayer(map, player)
  }

  def movePlayer(move: Movement): Map = {
    if (isAllowedMove(move)) {
      return new Map(map, player.move(move))
    }

    new Map(map, player)
  }

  // Checks if the allowed move is in bounds
  private def isAllowedMove(move: Movement): Boolean = {
    move match {
      case UP => player.a.y > 0 || player.b.y > 0
      case DOWN => player.a.y + 1 < map.length || player.b.y + 1 < map.length
      case LEFT => player.a.x > 0 || player.b.x > 0
      case RIGHT => player.a.x + 1 < map(0).length || player.b.x + 1 < map(0).length
    }
  }

  // Returns the game status
  def getGameStatus: GameStatus = {
    val (coveredFieldA, coveredFieldB) = getCoveredFields

    // Check if the target has been reached
    if (coveredFieldA.fieldType == FieldType.TARGET && player.isUpright) {
      return SUCCESS
    }

    // Check if the player is out of bounds
    if ((coveredFieldA.fieldType == FieldType.VOID) || (coveredFieldB.fieldType == FieldType.VOID)) {
      return FAILURE
    }

    // Check if the player is on a special tile
    if (coveredFieldA.fieldType == FieldType.SPECIAL && player.isUpright) {
      return FAILURE
    }

    ONGOING
  }

  // Returns the covered fields for each block of the player
  def getCoveredFields: (BoardField, BoardField) = {
    val x1 = player.a.x
    val y1 = player.a.y

    val coveredFieldA = map(y1)(x1)

    val x2 = player.b.x
    val y2 = player.b.y

    val coveredFieldB = map(y2)(x2)

    (coveredFieldA, coveredFieldB)
  }
}

package maps

import game.GameStatus.{FAILURE, GameStatus, ONGOING, SUCCESS}
import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}

class Map(map: Array[Array[BoardField]]) {
  var player: Player = new Player(Position(0, 0))
  var coveredFieldA: BoardField = BoardField(FieldType.START, Position(0, 0))
  var coveredFieldB: BoardField = BoardField(FieldType.START, Position(0, 0))

  // Shorthand for drawing the map
  def drawMap(): Unit = {
    MapDrawer.drawMap(map)
  }

  def initializePlayer(): Unit = {
    for {
      y <- map.indices
    } {
      for {
        x <- map(y).indices
      } {
        if (map(y)(x).fieldType == FieldType.START) {
          player = new Player(Position(x, y))
          coveredFieldA = BoardField(FieldType.START, Position(x, y))
          coveredFieldB = BoardField(FieldType.START, Position(x, y))
          map(y)(x) = BoardField(FieldType.BLOCK, Position(x, y))

          return
        }
      }
    }
  }

  def movePlayer(move: Movement): Unit = {
    if (isAllowedMove(move)) {
      val prevX1 = player.a.x
      val prevY1 = player.a.y

      val prevX2 = player.b.x
      val prevY2 = player.b.y

      player.move(move)

      val x1 = player.a.x
      val y1 = player.a.y

      val x2 = player.b.x
      val y2 = player.b.y


      map(prevY1)(prevX1) = coveredFieldA
      map(prevY2)(prevX2) = coveredFieldB

      coveredFieldA = map(y1)(x1)
      coveredFieldB = map(y2)(x2)

      map(y1)(x1) = BoardField(FieldType.BLOCK, Position(x1, y1))
      map(y2)(x2) = BoardField(FieldType.BLOCK, Position(x2, y2))
    }
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
}

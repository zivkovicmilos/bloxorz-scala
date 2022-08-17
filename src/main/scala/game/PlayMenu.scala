package game

import maps.Movement
import menu.MenuSwitcher

class PlayMenu(map: maps.Map) extends menu.Menu {
  var gameOver: Boolean = false

  override def display(): Unit = {
    map.drawMap()

    // TODO check if the game is over
    if (map.getGameStatus == GameStatus.SUCCESS) {
      println("Game is successfully finished! Press any key to go back.")
      gameOver = true
    }

    if (map.getGameStatus == GameStatus.FAILURE) {
      println("Game failed! Press any key to go back.")
      gameOver = true
    }

    // TODO display a message if the game is over
  }

  override def handleInput(input: String): Unit = {
    if (!gameOver) {
      input match {
        // TODO make this better
        case "u" => map.movePlayer(Movement.UP)
        case "d" => map.movePlayer(Movement.DOWN)
        case "l" => map.movePlayer(Movement.LEFT)
        case "r" => map.movePlayer(Movement.RIGHT)
        case _ =>
      }
    } else {
      input match {
        case _ => MenuSwitcher.goBack()
      }
    }

    // TODO handle only back input if the game is over
  }
}

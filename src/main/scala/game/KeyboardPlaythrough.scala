package game

import game.GameStatus.{FAILURE, GameStatus, ONGOING, SUCCESS}
import maps.Movement
import maps.Movement.{DOWN, LEFT, RIGHT, UP}
import menu.MenuSwitcher

import scala.annotation.tailrec

class KeyboardPlaythrough(map: maps.Map) extends menu.Menu {
  override def display(): Unit = {
    playWithKeyboard()
  }

  private def playWithKeyboard(): Unit = {
    val up = Movement.getChar(UP) + ""
    val down = Movement.getChar(DOWN) + ""
    val left = Movement.getChar(LEFT) + ""
    val right = Movement.getChar(RIGHT) + ""

    @tailrec
    def playGame(map: maps.Map): GameStatus = {
      map.drawMap()

      map.getGameStatus match {
        case SUCCESS => SUCCESS
        case FAILURE => FAILURE
        case ONGOING =>
          print("Next move: ")

          scala.io.StdIn.readLine() match {
            case `up` => playGame(map.movePlayer(UP))
            case `down` => playGame(map.movePlayer(DOWN))
            case `left` => playGame(map.movePlayer(LEFT))
            case `right` => playGame(map.movePlayer(RIGHT))
            case _ => playGame(map)
          }
      }
    }

    playGame(map) match {
      case SUCCESS => println("Game is successfully finished! Press any key to go back.")
      case FAILURE => println("Game failed! Press any key to go back.")
      case _ => MenuSwitcher.goBack()
    }
  }

  override def handleInput(input: String): Unit = {
    input match {
      case _ => MenuSwitcher.goBack()
    }
  }
}

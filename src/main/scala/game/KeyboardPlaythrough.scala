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
            case `up` => map.movePlayer(UP)
            case `down` => map.movePlayer(DOWN)
            case `left` => map.movePlayer(LEFT)
            case `right` => map.movePlayer(RIGHT)
            case _ =>
          }

          playGame(map)
      }
    }

    playGame(map) match {
      case SUCCESS => println("Game is successfully finished! Press any key to go back.")
      case FAILURE => println("Game failed! Press any key to go back.")
      case _ =>
    }
  }

  override def handleInput(input: String): Unit = {
    input match {
      case _ => MenuSwitcher.goBack()
    }
  }
}

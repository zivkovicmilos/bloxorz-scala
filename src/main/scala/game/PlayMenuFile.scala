package game

import game.GameStatus.{FAILURE, GameStatus, ONGOING, SUCCESS}
import maps.Movement.Movement
import menu.MenuSwitcher

import scala.annotation.tailrec

class PlayMenuFile(map: maps.Map, movements: List[Movement]) extends menu.Menu {
  override def display(): Unit = {
    playGameWithFile()
  }

  private def playGameWithFile(): Unit = {
    @tailrec
    def playGame(movements: List[Movement]): GameStatus = {
      movements match {
        case List() => GameStatus.ONGOING
        case head :: tail =>
          // TODO make this smoother
          print("\u001b[2J")
          map.drawMap()

          Thread.sleep(800)
          map.movePlayer(head)

          print("\u001b[2J")
          map.drawMap()

          if (map.getGameStatus == GameStatus.SUCCESS) {
            return GameStatus.SUCCESS
          }

          if (map.getGameStatus == GameStatus.FAILURE) {
            return GameStatus.FAILURE
          }

          playGame(tail)
      }
    }

    playGame(movements) match {
      case ONGOING => println("Unable to finish game with solution file")
      case SUCCESS => println("Game is successfully finished! Press any key to go back.")
      case FAILURE => println("Game failed! Press any key to go back.")
    }
  }

  override def handleInput(input: String): Unit = {
    input match {
      case _ => MenuSwitcher.goBack()
    }
  }
}

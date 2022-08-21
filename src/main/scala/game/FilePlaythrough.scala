package game

import game.GameStatus.{FAILURE, GameStatus, ONGOING, SUCCESS}
import maps.Movement.Movement
import menu.MenuSwitcher

import scala.annotation.tailrec

class FilePlaythrough(map: maps.Map, movements: List[Movement]) extends menu.Menu {
  override def display(): Unit = {
    playGameWithFile()
  }

  private def playGameWithFile(): Unit = {
    @tailrec
    def playGame(movements: List[Movement], map: maps.Map): GameStatus = {
      movements match {
        case List() => GameStatus.ONGOING
        case head :: tail =>
          // TODO make this smoother
          print("\u001b[2J")
          map.drawMap()

          Thread.sleep(800)
          val newMap = map.movePlayer(head)

          print("\u001b[2J")
          newMap.drawMap()

          if (newMap.getGameStatus == GameStatus.SUCCESS) {
            return GameStatus.SUCCESS
          }

          if (newMap.getGameStatus == GameStatus.FAILURE) {
            return GameStatus.FAILURE
          }

          playGame(tail, newMap)
      }
    }

    playGame(movements, map) match {
      case ONGOING => println("Unable to finish the game with the solution file")
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

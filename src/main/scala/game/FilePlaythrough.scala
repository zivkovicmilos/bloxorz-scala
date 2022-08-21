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
          val newMap = map.movePlayer(head)

          Thread.sleep(800) // For UX
          printMapWithClear(newMap)

          if (newMap.getGameStatus == GameStatus.SUCCESS) {
            return GameStatus.SUCCESS
          }

          if (newMap.getGameStatus == GameStatus.FAILURE) {
            return GameStatus.FAILURE
          }

          playGame(tail, newMap)
      }
    }

    printMapWithClear(map)

    playGame(movements, map) match {
      case ONGOING => println("Unable to finish the game with the solution file")
      case SUCCESS => println("Game is successfully finished! Press any key to go back.")
      case FAILURE => println("Game failed! Press any key to go back.")
    }
  }

  private def printMapWithClear(map: maps.Map): Unit = {
    print("\u001b[2J")
    map.drawMap()
  }

  override def handleInput(input: String): Unit = {
    input match {
      case _ => MenuSwitcher.goBack()
    }
  }
}

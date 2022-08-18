package game

import maps.Movement
import maps.Movement.Movement
import menu.MenuSwitcher

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.control.Breaks.{break, breakable}

class PlayMenuFile(map: maps.Map, fileName: String) extends menu.Menu {
  var gameOver: Boolean = false

  def loadMovements(): Array[Movement] = {
    val movementArr = ArrayBuffer[Movement]()
    val resource = Source.fromResource(fileName)

    // TODO handle malformed files
    for (line <- resource.getLines) {
      movementArr += Movement.getMovement(line(0))
    }

    resource.close()

    println(movementArr)

    movementArr.toArray
  }

  override def display(): Unit = {
    breakable {
      for (
        move <- loadMovements()
      ) {
        // TODO make this smoother
        print("\u001b[2J")
        map.drawMap()

        Thread.sleep(800)
        map.movePlayer(move)

        print("\u001b[2J")
        map.drawMap()

        if (map.getGameStatus == GameStatus.SUCCESS) {
          println("Game is successfully finished! Press any key to go back.")
          gameOver = true

          break
        }

        if (map.getGameStatus == GameStatus.FAILURE) {
          println("Game failed! Press any key to go back.")
          gameOver = true

          break
        }
      }
    }

    if (!gameOver) {
      println("Unable to finish game with solution file")
    }
  }

  override def handleInput(input: String): Unit = {
    if (gameOver) {
      input match {
        case _ => MenuSwitcher.goBack()
      }
    }
  }
}

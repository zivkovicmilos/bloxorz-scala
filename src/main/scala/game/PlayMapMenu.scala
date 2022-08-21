package game

import maps.Movement.Movement
import maps.{MapsManager, Movement}
import menu.MenuPrinter

import java.io.File
import scala.collection.mutable.ListBuffer
import scala.io.Source

class PlayMapMenu(selectedMap: Int) extends menu.Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      f"Map ${selectedMap}",
      List[String](
        "Play with keyboard",
        "Play with file",
        "Back"
      )
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      case "1" => playWithKeyboard()
      case "2" => playGameWithFile()
      case "3" => menu.MenuSwitcher.goBack()
      case _ =>
    }
  }

  private def playWithKeyboard(): Unit = {
    val map = new maps.Map(MapsManager.getMap(selectedMap))
    map.initializePlayer()

    menu.MenuSwitcher.goForward(new KeyboardPlaythrough(map))
  }

  private def playGameWithFile(): Unit = {
    // Get the path to the solution file
    print("File path: ")
    val fileName = scala.io.StdIn.readLine()

    try {
      val movements = loadMovementsFromFile(fileName)

      val map = new maps.Map(MapsManager.getMap(selectedMap))
      map.initializePlayer()

      menu.MenuSwitcher.goForward(new FilePlaythrough(map, movements))
    } catch {
      case e: Error => println(f"Error encountered while loading file: ${e.getMessage}")
    }
  }

  private def loadMovementsFromFile(fileName: String): List[Movement] = {
    val file = new File(fileName)

    if (!file.isFile || !file.exists()) {
      throw new Error(f"File $fileName is not a valid file path")
    }

    val movementArr = ListBuffer[Movement]()
    val resource = Source.fromFile(file)

    for (line <- resource.getLines) {
      movementArr += Movement.getMovement(line(0))
    }

    resource.close()

    if (movementArr.isEmpty) {
      // No movements found, invalid solution file
      throw new Error("Solution file does not contain movements")
    }

    movementArr.toList
  }
}

package game

import maps.FieldType.START
import maps.Movement.Movement
import maps.{MapsManager, Movement, Player}
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
    menu.MenuSwitcher.goForward(new KeyboardPlaythrough(loadMap))
  }

  private def loadMap: maps.Map = {
    val loadedMap = MapsManager.getMap(selectedMap)
    val startPosition = MapsManager.findFieldPosition(loadedMap, START)

    new maps.Map(loadedMap, Player(startPosition, startPosition))
  }

  private def playGameWithFile(): Unit = {
    // Get the path to the solution file
    print("File path: ")
    val fileName = scala.io.StdIn.readLine()

    try {
      val movements = loadMovementsFromFile(fileName)

      menu.MenuSwitcher.goForward(new FilePlaythrough(loadMap, movements))
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

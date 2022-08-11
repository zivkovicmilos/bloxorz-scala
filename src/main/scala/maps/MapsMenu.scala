package maps

import menu.{Menu, MenuPrinter, MenuSwitcher}

import java.io.File

object MapsMenu extends Menu {
  var feedback: String = ""

  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Maps",
      List[String](
        "Load New Map",
        "Edit Map",
        "Back"
      )
    )

    if (feedback != "") {
      println(feedback)
    }
  }

  def loadMapFromFile(): Unit = {
    print("File name: ")
    val fileName = scala.io.StdIn.readLine()

    // TODO handle missing files
    val resource = getClass.getResource(fileName)

    MapsManager.addMap(new File(resource.getPath))

    feedback = "Map successfully added!\n"
  }

  override def handleInput(input: String): Unit = {
    feedback = ""

    input match {
      case "1" => loadMapFromFile()
      case "3" => MenuSwitcher.goBack()
      case _ =>
    }
  }
}

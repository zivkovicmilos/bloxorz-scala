package maps

import menu.{Menu, MenuPrinter, MenuSwitcher}

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

    try {
      MapsManager.addMapFromFile(fileName)

      feedback = "Map successfully added!\n"
    } catch {
      case e: Error => feedback = f"Unable to add specified map file: ${e.getMessage}"
    }
  }

  override def handleInput(input: String): Unit = {
    feedback = ""

    input match {
      case "1" => loadMapFromFile()
      case "2" => MenuSwitcher.goForward(editor.EditorMenu)
      case "3" => MenuSwitcher.goBack()
      case _ =>
    }
  }
}

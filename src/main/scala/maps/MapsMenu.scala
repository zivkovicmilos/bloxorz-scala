package maps

import menu.{Menu, MenuPrinter, MenuSwitcher}

object MapsMenu extends Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Maps",
      List[String](
        "Load New Map",
        "Edit Map",
        "Back"
      )
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      case "3" => MenuSwitcher.goBack()
      case _ =>
    }
  }
}

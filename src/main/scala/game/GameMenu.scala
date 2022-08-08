package game

import menu.MenuPrinter

object GameMenu extends menu.Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Start Game",
      List[String](
        "???",
        "Back",
      )
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      case "2" => menu.MenuSwitcher.goBack()
      case _ =>
    }
  }
}

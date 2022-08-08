package solver

import menu.{Menu, MenuPrinter, MenuSwitcher}

object SolverMenu extends Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Solver",
      List[String](
        "Choose Map",
        "Back"
      )
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      case "2" => MenuSwitcher.goBack()
      case _ =>
    }
  }
}


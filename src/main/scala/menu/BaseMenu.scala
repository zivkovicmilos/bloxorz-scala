package menu

import game.GameMenu
import maps.MapsMenu
import solver.SolverMenu

object BaseMenu extends Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Base Menu",
      List[String](
        "Start game",
        "Maps",
        "Solver",
        "Exit"
      )
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      case "1" => MenuSwitcher.goForward(GameMenu)
      case "2" => MenuSwitcher.goForward(MapsMenu)
      case "3" => MenuSwitcher.goForward(SolverMenu)
      case "4" => MenuSwitcher.goBack()
      case _ =>
    }
  }
}

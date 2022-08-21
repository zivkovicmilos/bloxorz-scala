package editor

import maps.MapsManager
import menu.MenuPrinter

object EditorMenu extends menu.Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Editor",
      MapsManager.getMapNames ::: List("Back")
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      case y if y == "" =>
      case x if x.forall(Character.isDigit) && x.toInt == MapsManager.getNumMaps + 1 => menu.MenuSwitcher.goBack()
      case n if n.forall(Character.isDigit) && n.toInt <= MapsManager.getNumMaps =>
        menu.MenuSwitcher.goForward(new EditMapMenu(MapsManager.getMap(n.toInt)))
      case _ =>
    }
  }
}

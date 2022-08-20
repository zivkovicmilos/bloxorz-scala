package editor

import maps.MapsManager
import menu.MenuPrinter

import scala.collection.mutable.ListBuffer

object EditorMenu extends menu.Menu {
  override def display(): Unit = {
    val menuItems: ListBuffer[String] = ListBuffer[String]()

    for (
      i <- 1 to MapsManager.getNumMaps
    ) {
      menuItems += "Map %d".format(i)
    }

    menuItems += "Back"

    MenuPrinter.printMenu(
      "Editor",
      menuItems.toList
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      // TODO handle this better
      case "back" => menu.MenuSwitcher.goBack()
      case n =>
        menu.MenuSwitcher.goForward(new EditMapMenu(MapsManager.getMap(1)))
    }
  }
}

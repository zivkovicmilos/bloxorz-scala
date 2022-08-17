package game

import maps.MapsManager
import menu.MenuPrinter

import scala.collection.mutable.ListBuffer

object GameMenu extends menu.Menu {
  var numMenuItems = 0

  override def display(): Unit = {
    val numMaps = MapsManager.getNumMaps

    val menuItems: ListBuffer[String] = ListBuffer[String]()

    for (
      i <- 1 to numMaps
    ) {
      menuItems += "Map %d".format(i)
    }

    menuItems += "Back"

    MenuPrinter.printMenu(
      "Start Game",
      menuItems.toList
    )

    numMenuItems = menuItems.size
  }

  override def handleInput(input: String): Unit = {
    val backItem = numMenuItems.toString

    input match {
      case "2" => menu.MenuSwitcher.goBack() // TODO fix this option
      case selectedMap => {
        // TODO handle bad input
        PlayMapMenu.setSelectedMap(selectedMap.toInt)

        menu.MenuSwitcher.goForward(PlayMapMenu)
      }
    }
  }
}

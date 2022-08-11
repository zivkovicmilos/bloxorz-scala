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
      case backItem => menu.MenuSwitcher.goBack()
      case _ =>
    }
  }
}

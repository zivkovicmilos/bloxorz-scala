package solver

import maps.MapsManager
import menu.{Menu, MenuPrinter}

import scala.collection.mutable.ListBuffer

object SolverMenu extends Menu {
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
      "Solver",
      menuItems.toList
    )

    numMenuItems = menuItems.size
  }

  override def handleInput(input: String): Unit = {
    val backItem = numMenuItems.toString

    input match {
      case "2" => menu.MenuSwitcher.goBack() // TODO fix this option
      case _ => {
        // TODO remove print
        //        MapDrawer.drawMap(MapsManager.getMap(1))
        //
        //        val solution = MapSolver.solveMap(MapsManager.getMap(1))
        //
        //        println(solution)
      }
    }
  }
}


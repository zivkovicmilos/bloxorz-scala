package solver

import maps.Movement.Movement
import maps.{MapDrawer, MapsManager, Movement}
import menu.{Menu, MenuPrinter}

import java.io.{BufferedWriter, File, FileWriter}
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
        MapDrawer.drawMap(MapsManager.getMap(1))

        val solution = MapSolver.runSolver(MapsManager.getMap(1))

        saveSolution(solution, 1)
      }
    }
  }

  private def saveSolution(solution: List[Movement], mapIndex: Int): Unit = {
    val file = new File(f"${MapsManager.mapPrefix}${mapIndex}_solution.txt")
    val bw = new BufferedWriter(new FileWriter(file))

    for (
      move <- solution
    ) {
      bw.write(f"${Movement.getMovementCharacter(move)}\n")
    }

    bw.close()
  }
}


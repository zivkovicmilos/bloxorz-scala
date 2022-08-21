package solver

import maps.Movement.Movement
import maps.{MapsManager, Movement}
import menu.{Menu, MenuPrinter}

import java.io.{BufferedWriter, File, FileWriter}

object SolverMenu extends Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Solver",
      MapsManager.getMapNames ::: List[String]("Back")
    )
  }

  override def handleInput(input: String): Unit = {
    input match {
      case y if y == "" =>
      case x if x.forall(Character.isDigit) && x.toInt == MapsManager.getNumMaps + 1 => menu.MenuSwitcher.goBack()
      case n if n.forall(Character.isDigit) && n.toInt <= MapsManager.getNumMaps =>
        saveSolution(
          MapSolver.runSolver(MapsManager.getMap(n.toInt)),
          n.toInt
        )

        // For UX
        print("Press any key to continue.")
        scala.io.StdIn.readLine()
      case _ =>
    }
  }

  // Saves the solution to a local file in the format: map_index_solution.txt
  private def saveSolution(solution: List[Movement], mapIndex: Int): Unit = {
    if (solution.isEmpty) {
      // No need to save an empty solution to a file
      return
    }

    val fileName = f"${MapsManager.mapPrefix}${mapIndex}_solution.txt"
    val file = new File(fileName)
    val bw = new BufferedWriter(new FileWriter(file))

    for (
      move <- solution
    ) {
      bw.write(f"${Movement.getChar(move)}\n")
    }

    bw.close()

    println(f"Solution saved to: $fileName\n")
  }
}


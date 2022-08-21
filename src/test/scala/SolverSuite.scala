import maps.FieldType.{GROUND, SPECIAL, START, TARGET, VOID}
import maps.{BoardField, MapsManager, Position}
import org.scalatest.funsuite.AnyFunSuite
import solver.MapSolver

class SolverSuite extends AnyFunSuite {
  test("should find the solution for each predefined map iteratively") {
    // Load predefined maps
    MapsManager.loadMaps()

    for (
      mapIndex <- 1 to MapsManager.getNumMaps
    ) {
      val loadedMap = MapsManager.getMap(mapIndex)

      val solution = MapSolver.runSolverIterative(loadedMap)

      assert(solution.nonEmpty)
    }
  }

  test("should find a solution for each predefined map recursively") {
    // Load predefined maps
    MapsManager.loadMaps()

    for (
      mapIndex <- 1 to MapsManager.getNumMaps
    ) {
      val loadedMap = MapsManager.getMap(mapIndex)

      val solution = MapSolver.runSolver(loadedMap)

      assert(solution.nonEmpty)
    }
  }

  test("should not be able to find a solution for a faulty map") {
    /*
    ––-–––––– 0
    –ooo.–––– 1
    –oSo-oTo– 2
    –ooo----- 3
    --------- 4
     */
    val invalidMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(VOID, Position(0, 0)), // 0
        BoardField(VOID, Position(1, 0)), // 1
        BoardField(VOID, Position(2, 0)), // 2
        BoardField(VOID, Position(3, 0)), // 3
        BoardField(VOID, Position(4, 0)), // 4
        BoardField(VOID, Position(5, 0)), // 5
        BoardField(VOID, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
      // Row 1
      Array[BoardField](
        BoardField(VOID, Position(0, 1)), // 0
        BoardField(GROUND, Position(1, 1)), // 1
        BoardField(GROUND, Position(2, 1)), // 2
        BoardField(GROUND, Position(3, 1)), // 3
        BoardField(SPECIAL, Position(4, 1)), // 4
        BoardField(VOID, Position(5, 1)), // 5
        BoardField(VOID, Position(6, 1)), // 6
        BoardField(VOID, Position(7, 1)), // 7
        BoardField(VOID, Position(8, 1)) // 8
      ),
      // Row 2
      Array[BoardField](
        BoardField(VOID, Position(0, 2)), // 0
        BoardField(GROUND, Position(1, 2)), // 1
        BoardField(START, Position(2, 2)), // 2
        BoardField(GROUND, Position(3, 2)), // 3

        // Changing this field to GROUND would yield a solution
        BoardField(VOID, Position(4, 2)), // 4

        BoardField(GROUND, Position(5, 2)), // 5
        BoardField(TARGET, Position(6, 2)), // 6
        BoardField(GROUND, Position(7, 2)), // 7
        BoardField(VOID, Position(8, 2)) // 8
      ),
      // Row 3
      Array[BoardField](
        BoardField(VOID, Position(0, 3)), // 0
        BoardField(GROUND, Position(1, 3)), // 1
        BoardField(GROUND, Position(2, 3)), // 2
        BoardField(GROUND, Position(3, 3)), // 3
        BoardField(VOID, Position(4, 3)), // 4
        BoardField(VOID, Position(5, 3)), // 5
        BoardField(VOID, Position(6, 3)), // 6
        BoardField(VOID, Position(7, 3)), // 7
        BoardField(VOID, Position(8, 3)) // 8
      ),
      // Row 4
      Array[BoardField](
        BoardField(VOID, Position(0, 0)), // 0
        BoardField(VOID, Position(1, 0)), // 1
        BoardField(VOID, Position(2, 0)), // 2
        BoardField(VOID, Position(3, 0)), // 3
        BoardField(VOID, Position(4, 0)), // 4
        BoardField(VOID, Position(5, 0)), // 5
        BoardField(VOID, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val solutions = MapSolver.runSolver(invalidMap)

    assert(solutions.isEmpty)
  }
}
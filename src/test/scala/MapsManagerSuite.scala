import maps.FieldType.{SPECIAL, START, TARGET, VOID}
import maps.{BoardField, MapsManager, Position}
import org.scalatest.funsuite.AnyFunSuite

class MapsManagerSuite extends AnyFunSuite {
  test("should load predefined maps") {
    MapsManager.loadMaps()

    assert(MapsManager.getNumMaps == 5)
  }

  test("should be able to validate a map without a START field") {
    // T--–––––– 0
    val invalidMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(TARGET, Position(0, 0)), // 0
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

    assert(!MapsManager.isValidMap(invalidMap))
  }

  test("should be able to validate a map without a TARGET field") {
    // S--–––––– 0
    val invalidMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
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

    assert(!MapsManager.isValidMap(invalidMap))
  }

  test("should be able to validate a map") {
    // S--–––––T 0
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(VOID, Position(1, 0)), // 1
        BoardField(VOID, Position(2, 0)), // 2
        BoardField(VOID, Position(3, 0)), // 3
        BoardField(VOID, Position(4, 0)), // 4
        BoardField(VOID, Position(5, 0)), // 5
        BoardField(VOID, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(TARGET, Position(8, 0)) // 8
      ),
    )

    assert(MapsManager.isValidMap(validMap))
  }

  test("should be able to locate the specified field") {
    val expectedPosition = Position(5, 0)
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(VOID, Position(1, 0)), // 1
        BoardField(VOID, Position(2, 0)), // 2
        BoardField(VOID, Position(3, 0)), // 3
        BoardField(VOID, Position(4, 0)), // 4
        BoardField(TARGET, expectedPosition), // 5
        BoardField(VOID, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    assert(MapsManager.findFieldPosition(validMap, TARGET) == expectedPosition)
  }

  test("should not be able to locate the specified field") {
    val expectedPosition = Position(-1, -1)
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(VOID, Position(1, 0)), // 1
        BoardField(VOID, Position(2, 0)), // 2
        BoardField(VOID, Position(3, 0)), // 3
        BoardField(VOID, Position(4, 0)), // 4
        BoardField(TARGET, expectedPosition), // 5
        BoardField(VOID, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    assert(MapsManager.findFieldPosition(validMap, SPECIAL) == expectedPosition)
  }

  test("should not produce a missing map on fetch") {
    // Load predefined maps
    MapsManager.loadMaps()

    assertThrows[Error] {
      MapsManager.getMap(MapsManager.getNumMaps + 1)
    }
  }
}
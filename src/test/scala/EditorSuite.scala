import editor.{DefaultOperation, EditMapMenu, EditOperation}
import maps.FieldType.{GROUND, SPECIAL, START, TARGET, VOID}
import maps.{BoardField, MapsManager, Position}
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable.ListBuffer

class EditorSuite extends AnyFunSuite {
  private def findOperation(name: String, operations: ListBuffer[EditOperation]): EditOperation = {
    for (
      operation <- operations
    ) {
      if (name.equals(operation.getName)) {
        return operation
      }
    }

    operations.head
  }

  test("should remove an edge tile") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(GROUND, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val pos = Position(7, 0)
    assert(validMap(pos.y)(pos.x).fieldType == GROUND)

    editor.convertTile(GROUND, VOID, pos)

    assert(validMap(pos.y)(pos.x).fieldType == VOID)
  }

  test("should add a GROUND tile to an edge") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val pos = Position(7, 0)
    assert(validMap(pos.y)(pos.x).fieldType == VOID)

    editor.addTileToEdge(pos)

    assert(validMap(pos.y)(pos.x).fieldType == GROUND)
  }

  test("should turn a GROUND tile into a SPECIAL one") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val pos = Position(6, 0)
    assert(validMap(pos.y)(pos.x).fieldType == GROUND)

    editor.swapFields(GROUND, SPECIAL, pos)

    assert(validMap(pos.y)(pos.x).fieldType == SPECIAL)
  }

  test("should turn a SPECIAL tile into a GROUND one") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(SPECIAL, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val pos = Position(6, 0)
    assert(validMap(pos.y)(pos.x).fieldType == SPECIAL)

    editor.swapFields(SPECIAL, GROUND, pos)

    assert(validMap(pos.y)(pos.x).fieldType == GROUND)
  }

  test("should set a new START position") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val oldStart = Position(0, 0)
    val newStart = Position(1, 0)

    assert(validMap(oldStart.y)(oldStart.x).fieldType == START)
    assert(validMap(newStart.y)(newStart.x).fieldType == GROUND)

    editor.swapFieldWithGround(START, newStart)

    assert(validMap(oldStart.y)(oldStart.x).fieldType == GROUND)
    assert(validMap(newStart.y)(newStart.x).fieldType == START)
  }

  test("should set a new TARGET position") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(TARGET, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val oldTarget = Position(0, 0)
    val newTarget = Position(1, 0)

    assert(validMap(oldTarget.y)(oldTarget.x).fieldType == TARGET)
    assert(validMap(newTarget.y)(newTarget.x).fieldType == GROUND)

    editor.swapFieldWithGround(TARGET, newTarget)

    assert(validMap(oldTarget.y)(oldTarget.x).fieldType == GROUND)
    assert(validMap(newTarget.y)(newTarget.x).fieldType == TARGET)
  }

  test("should swap START and TARGET positions") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(TARGET, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val startPos = Position(0, 0)
    val targetPos = Position(6, 0)

    assert(validMap(startPos.y)(startPos.x).fieldType == START)
    assert(validMap(targetPos.y)(targetPos.x).fieldType == TARGET)

    editor.swapStartAndTarget()

    assert(validMap(startPos.y)(startPos.x).fieldType == TARGET)
    assert(validMap(targetPos.y)(targetPos.x).fieldType == START)
  }

  test("should swap all SPECIAL tiles to GROUND") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(SPECIAL, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(SPECIAL, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(SPECIAL, Position(5, 0)), // 5
        BoardField(TARGET, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)

    val specialPositions = List[Position](
      Position(1, 0),
      Position(3, 0),
      Position(5, 0),
    )

    for (
      specialPos <- specialPositions
    ) {
      assert(validMap(specialPos.y)(specialPos.x).fieldType == SPECIAL)
    }

    editor.swapAllSpecial()

    assert(MapsManager.findFieldPosition(validMap, SPECIAL) == Position(-1, -1))
  }

  test("should filter all SPECIAL tiles") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(VOID, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(SPECIAL, Position(5, 0)), // 5
        BoardField(TARGET, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)
    val pos = Position(3, 0)

    assert(validMap(pos.y)(pos.x).fieldType == VOID)

    editor.filterSpecial(pos, 2)

    assert(validMap(pos.y)(pos.x).fieldType == GROUND)
  }

  test("should define and run a list of operations") {
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(START, Position(0, 0)), // 0
        BoardField(GROUND, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(SPECIAL, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(TARGET, Position(6, 0)), // 6
        BoardField(VOID, Position(7, 0)), // 7
        BoardField(VOID, Position(8, 0)) // 8
      ),
    )

    val editor = new EditMapMenu(validMap)
    val operationName = "Swap And Convert"

    val startPos = Position(0, 0)
    val targetPos = Position(6, 0)
    val specialPos = Position(3, 0)

    assert(validMap(startPos.y)(startPos.x).fieldType == START)
    assert(validMap(targetPos.y)(targetPos.x).fieldType == TARGET)
    assert(validMap(specialPos.y)(specialPos.x).fieldType == SPECIAL)


    editor.defineOperationList(
      operationName,
      ListBuffer[EditOperation](
        findOperation(DefaultOperation.getName(DefaultOperation.INVERT), editor.availableOperations),
        findOperation(DefaultOperation.getName(DefaultOperation.SWAP_ALL_SPECIAL), editor.availableOperations)
      )
    )

    val op = findOperation(operationName, editor.availableOperations)

    assert(operationName.equals(op.getName))

    op.run()

    assert(validMap(startPos.y)(startPos.x).fieldType == TARGET)
    assert(validMap(targetPos.y)(targetPos.x).fieldType == START)
    assert(validMap(specialPos.y)(specialPos.x).fieldType == GROUND)
  }
}
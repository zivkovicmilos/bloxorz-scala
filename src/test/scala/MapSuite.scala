import game.GameStatus.{FAILURE, ONGOING, SUCCESS}
import maps.FieldType.{GROUND, SPECIAL, START, TARGET}
import maps.Movement.{LEFT, RIGHT}
import maps.{BoardField, Player, Position}
import org.scalatest.funsuite.AnyFunSuite

class MapSuite extends AnyFunSuite {
  test("should correctly move the player") {
    // Soooooooo
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
        BoardField(GROUND, Position(8, 0)) // 8
      ),
    )

    val map = new maps.Map(validMap, Player(Position(0, 0), Position(0, 0)))
    val (coveredAPre, coveredBPre) = map.getCoveredFields

    assert(coveredAPre.fieldType == START)
    assert(coveredAPre.position == Position(0, 0))

    assert(coveredBPre.fieldType == START)
    assert(coveredBPre.position == Position(0, 0))

    val m = map.movePlayer(RIGHT)

    val (coveredA, coveredB) = m.getCoveredFields

    assert(coveredA.fieldType == GROUND)
    assert(coveredA.position == Position(2, 0))

    assert(coveredB.fieldType == GROUND)
    assert(coveredB.position == Position(1, 0))

    assert(m.getGameStatus == ONGOING)
  }

  test("should correctly fail to move the player") {
    // oSooooooo
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(GROUND, Position(0, 0)), // 0
        BoardField(START, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(GROUND, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(GROUND, Position(7, 0)), // 7
        BoardField(GROUND, Position(8, 0)) // 8
      ),
    )

    val startPos = Position(1, 0)

    val map = new maps.Map(validMap, Player(startPos, startPos))
    val (coveredAPre, coveredBPre) = map.getCoveredFields

    assert(coveredAPre.fieldType == START)
    assert(coveredAPre.position == startPos)

    assert(coveredBPre.fieldType == START)
    assert(coveredBPre.position == startPos)

    val m = map.movePlayer(LEFT)

    val (coveredA, coveredB) = m.getCoveredFields

    assert(coveredA.fieldType == START)
    assert(coveredA.position == startPos)

    assert(coveredB.fieldType == START)
    assert(coveredB.position == startPos)

    assert(m.getGameStatus == ONGOING)
  }

  test("should correctly finish the game") {
    // oSooToooo
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(GROUND, Position(0, 0)), // 0
        BoardField(START, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(TARGET, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(GROUND, Position(7, 0)), // 7
        BoardField(GROUND, Position(8, 0)) // 8
      ),
    )

    val startPos = Position(1, 0)
    val targetPos = Position(4, 0)

    val map = new maps.Map(validMap, Player(startPos, startPos))
    val (coveredAPre, coveredBPre) = map.getCoveredFields

    assert(coveredAPre.fieldType == START)
    assert(coveredAPre.position == startPos)

    assert(coveredBPre.fieldType == START)
    assert(coveredBPre.position == startPos)

    val m = map.movePlayer(RIGHT).movePlayer(RIGHT)

    val (coveredA, coveredB) = m.getCoveredFields

    assert(coveredA.fieldType == TARGET)
    assert(coveredA.position == targetPos)

    assert(coveredB.fieldType == TARGET)
    assert(coveredB.position == targetPos)

    assert(m.getGameStatus == SUCCESS)
  }

  test("should correctly fail the game") {
    // oSoo.oooo
    val validMap: Array[Array[BoardField]] = Array[Array[BoardField]](
      // Row 0
      Array[BoardField](
        BoardField(GROUND, Position(0, 0)), // 0
        BoardField(START, Position(1, 0)), // 1
        BoardField(GROUND, Position(2, 0)), // 2
        BoardField(GROUND, Position(3, 0)), // 3
        BoardField(SPECIAL, Position(4, 0)), // 4
        BoardField(GROUND, Position(5, 0)), // 5
        BoardField(GROUND, Position(6, 0)), // 6
        BoardField(GROUND, Position(7, 0)), // 7
        BoardField(GROUND, Position(8, 0)) // 8
      ),
    )

    val startPos = Position(1, 0)
    val specialPos = Position(4, 0)

    val map = new maps.Map(validMap, Player(startPos, startPos))
    val (coveredAPre, coveredBPre) = map.getCoveredFields

    assert(coveredAPre.fieldType == START)
    assert(coveredAPre.position == startPos)

    assert(coveredBPre.fieldType == START)
    assert(coveredBPre.position == startPos)

    val m = map.movePlayer(RIGHT).movePlayer(RIGHT)

    val (coveredA, coveredB) = m.getCoveredFields

    assert(coveredA.fieldType == SPECIAL)
    assert(coveredA.position == specialPos)

    assert(coveredB.fieldType == SPECIAL)
    assert(coveredB.position == specialPos)

    assert(m.getGameStatus == FAILURE)
  }
}
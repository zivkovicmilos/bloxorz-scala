import maps.FieldType.{BLOCK, FieldType, GROUND, SPECIAL, START, TARGET, VOID}
import maps.{BoardField, FieldType, Position}
import org.scalatest.funsuite.AnyFunSuite

import scala.util.Random

class BoardFieldSuite extends AnyFunSuite {
  val RNG: Random = scala.util.Random
  val nullPos: Position = Position(0, 0)

  test("should have the correct position") {
    val position = Position(RNG.nextInt(), RNG.nextInt())
    val bf = BoardField(VOID, Position(position.x, position.y))

    assert(bf.position == position)
  }

  test("should have the correct color") {
    val testTable: List[(FieldType, String)] = List[(FieldType, String)](
      (VOID, Console.WHITE),
      (START, Console.CYAN),
      (TARGET, Console.RED),
      (SPECIAL, Console.YELLOW),
      (GROUND, Console.GREEN),
      (BLOCK, Console.MAGENTA)
    )

    for (
      testCase <- testTable
    ) {
      val bf = BoardField(testCase._1, nullPos)

      assert(FieldType.getColor(bf.fieldType).equals(testCase._2))
    }
  }

  test("should have the correct array") {
    val color = Console.WHITE
    val expectedArray = Array(
      Array(color + "+", color + "-", color + "-", color + "-", color + "+"),
      Array(color + "|", " ", color + FieldType.getValue(VOID), " ", color + "|"),
      Array(color + "+", color + "-", color + "-", color + "-", color + "+"),
    )

    val bf = BoardField(VOID, nullPos).getBoardField

    // Make sure the sizes are the same
    assert(bf.length == expectedArray.length)
    for (
      i <- bf.indices
    ) {
      assert(bf(i).length == expectedArray(i).length)
    }

    // Make sure the elements are the same
    for (
      i <- bf.indices
    ) {
      assert(bf(i) sameElements expectedArray(i))
    }
  }
}
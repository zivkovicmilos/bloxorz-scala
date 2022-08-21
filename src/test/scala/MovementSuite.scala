import maps.Movement
import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}
import org.scalatest.funsuite.AnyFunSuite

class MovementSuite extends AnyFunSuite {
  val testTable: List[(Movement, Char)] = List[(Movement, Char)](
    (UP, 'u'),
    (DOWN, 'd'),
    (LEFT, 'l'),
    (RIGHT, 'r')
  )

  test("should return the proper movement from char") {
    for (
      testCase <- testTable
    ) {
      assert(Movement.getChar(testCase._1).equals(testCase._2))
    }
  }

  test("should return the proper char from movement") {
    for (
      testCase <- testTable
    ) {
      assert(Movement.getMovement(testCase._2).equals(testCase._1))
    }
  }
}
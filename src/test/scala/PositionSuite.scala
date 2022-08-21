import maps.Movement.{DOWN, LEFT, RIGHT, UP}
import maps.Position
import org.scalatest.funsuite.AnyFunSuite

class PositionSuite extends AnyFunSuite {
  test("should move correctly up") {
    assert(Position(0, 1).move(UP) == Position(0, 0))
  }

  test("should move correctly down") {
    assert(Position(0, 0).move(DOWN) == Position(0, 1))
  }

  test("should move correctly left") {
    assert(Position(1, 0).move(LEFT) == Position(0, 0))
  }

  test("should move correctly right") {
    assert(Position(0, 0).move(RIGHT) == Position(1, 0))
  }

  test("should be in the same row") {
    assert(Position(1, 0).isSameRow(Position(2, 0)))
  }

  test("should be in the same column") {
    assert(Position(1, 0).isSameRow(Position(2, 0)))
  }

  test("should be left to") {
    assert(Position(1, 0).isLeftTo(Position(2, 0)))
  }

  test("should be right to") {
    assert(Position(2, 0).isRightTo(Position(1, 0)))
  }

  test("should be up to") {
    assert(Position(0, 0).isUpTo(Position(0, 1)))
  }

  test("should be down to") {
    assert(Position(0, 1).isDownTo(Position(0, 0)))
  }

  test("should have the correct column distance") {
    assert(Position(1, 0).columnDistance(Position(2, 0)) == 1)
  }

  test("should have the correct row distance") {
    assert(Position(0, 1).rowDistance(Position(0, 2)) == 1)
  }
}
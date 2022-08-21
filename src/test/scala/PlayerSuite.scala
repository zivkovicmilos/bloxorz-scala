import maps.Movement.{DOWN, LEFT, RIGHT, UP}
import maps.{Player, Position}
import org.scalatest.funsuite.AnyFunSuite

class PlayerSuite extends AnyFunSuite {
  test("should move properly up if upright") {
    val startingPosition = Position(5, 5)
    val expectedA = Position(5, 3)
    val expectedB = Position(5, 4)

    val player = Player(startingPosition, startingPosition)

    assert(player.isUpright)

    val p = player.move(UP)

    assert(p.a.isSameColumn(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should roll properly up if in the same row") {
    val expectedA = Position(5, 4)
    val expectedB = Position(6, 4)

    val player = Player(Position(5, 5), Position(6, 5))

    assert(!player.isUpright)

    val p = player.move(UP)

    assert(p.a.isSameRow(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should move the block with the highest row up") {
    val expectedPos = Position(0, 0)

    // b has a higher row number than a (it is below a)
    val player = Player(Position(0, 1), Position(0, 2))

    assert(!player.isUpright)

    val p = player.move(UP)

    assert(p.a.isSameColumn(p.b))
    assert(p.isUpright)
    assert(p.a == expectedPos)
    assert(p.b == expectedPos)
  }

  test("should move properly down if upright") {
    val startingPosition = Position(5, 5)
    val expectedA = Position(5, 7)
    val expectedB = Position(5, 6)

    val player = Player(startingPosition, startingPosition)

    assert(player.isUpright)

    val p = player.move(DOWN)

    assert(p.a.isSameColumn(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should roll properly down if in the same row") {
    val expectedA = Position(5, 6)
    val expectedB = Position(6, 6)

    val player = Player(Position(5, 5), Position(6, 5))

    assert(!player.isUpright)

    val p = player.move(DOWN)

    assert(p.a.isSameRow(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should move the block with the lowest row down") {
    val expectedPos = Position(0, 3)

    // b has a higher row number than a (it is below a)
    val player = Player(Position(0, 1), Position(0, 2))

    assert(!player.isUpright)

    val p = player.move(DOWN)

    assert(p.a.isSameColumn(p.b))
    assert(p.isUpright)
    assert(p.a == expectedPos)
    assert(p.b == expectedPos)
  }

  test("should roll properly left if upright") {
    val startingPosition = Position(5, 0)
    val expectedA = Position(3, 0)
    val expectedB = Position(4, 0)

    val player = Player(startingPosition, startingPosition)

    assert(player.isUpright)

    val p = player.move(LEFT)

    assert(p.a.isSameRow(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should roll properly left if in the same column") {
    val expectedA = Position(5, 0)
    val expectedB = Position(5, 1)

    val player = Player(Position(6, 0), Position(6, 1))

    assert(!player.isUpright)

    val p = player.move(LEFT)

    assert(p.a.isSameColumn(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should move the block that is rightest to the left") {
    val expectedA = Position(4, 0)
    val expectedB = Position(4, 0)

    // b is to the right of a
    val player = Player(Position(5, 0), Position(6, 0))

    assert(!player.isUpright)

    val p = player.move(LEFT)

    assert(p.isUpright)
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should roll properly right if upright") {
    val startingPosition = Position(5, 0)
    val expectedA = Position(7, 0)
    val expectedB = Position(6, 0)

    val player = Player(startingPosition, startingPosition)

    assert(player.isUpright)

    val p = player.move(RIGHT)

    assert(p.a.isSameRow(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should roll properly right if in the same column") {
    val expectedA = Position(6, 0)
    val expectedB = Position(6, 1)

    val player = Player(Position(5, 0), Position(5, 1))

    assert(!player.isUpright)

    val p = player.move(RIGHT)

    assert(p.a.isSameColumn(p.b))
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }

  test("should move the block that is leftest to the right") {
    val expectedA = Position(6, 0)
    val expectedB = Position(6, 0)

    // a is to the left of b
    val player = Player(Position(4, 0), Position(5, 0))

    assert(!player.isUpright)

    val p = player.move(RIGHT)

    assert(p.isUpright)
    assert(p.a == expectedA)
    assert(p.b == expectedB)
  }
}
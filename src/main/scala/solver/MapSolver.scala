package solver

import maps.FieldType.START
import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}
import maps._

import scala.annotation.tailrec
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object MapSolver {
  // The current player position, the next move, the list of previous movements
  type PlayerMove = (Player, Movement, ListBuffer[Movement])

  // Runs an iterative version of the solution algorithm that finds the shortest
  // path to the solution, if there is any
  def runSolverIterative(map: Array[Array[BoardField]]): List[Movement] = {
    // Find the starting position
    val start = maps.MapsManager.findFieldPosition(map, START)
    val startPlayer = new Player(start)

    // All found paths
    val foundPaths: ListBuffer[ListBuffer[Movement]] = ListBuffer[ListBuffer[Movement]]()
    val visited: ArrayBuffer[Player] = ArrayBuffer[Player]()

    visited += startPlayer

    var possibleMoves: ListBuffer[PlayerMove] = ListBuffer[PlayerMove]()
    if (canMove(startPlayer, map, UP)) {
      possibleMoves += ((startPlayer, UP, ListBuffer[Movement]()))
    }

    if (canMove(startPlayer, map, DOWN)) {
      possibleMoves += ((startPlayer, DOWN, ListBuffer[Movement]()))
    }

    if (canMove(startPlayer, map, RIGHT)) {
      possibleMoves += ((startPlayer, RIGHT, ListBuffer[Movement]()))
    }

    if (canMove(startPlayer, map, LEFT)) {
      possibleMoves += ((startPlayer, LEFT, ListBuffer[Movement]()))
    }

    while (possibleMoves.nonEmpty) {
      val possibleMove = possibleMoves.head
      val p = possibleMove._1.copy()
      val movement = possibleMove._2
      val prevMovements = possibleMove._3.clone()

      p.move(movement)

      visited += p

      prevMovements += movement

      if (p.isUpright && map(p.a.y)(p.a.x).fieldType == FieldType.TARGET) {
        // Add the current path to the list of correct paths
        foundPaths += prevMovements
      } else {
        if (canMove(p, map, UP) && notVisited(visited, p, UP)) {
          possibleMoves += ((p, UP, prevMovements))
        }

        if (canMove(p, map, DOWN) && notVisited(visited, p, DOWN)) {
          possibleMoves += ((p, DOWN, prevMovements))
        }

        if (canMove(p, map, RIGHT) && notVisited(visited, p, RIGHT)) {
          possibleMoves += ((p, RIGHT, prevMovements))
        }

        if (canMove(p, map, LEFT) && notVisited(visited, p, LEFT)) {
          possibleMoves += ((p, LEFT, prevMovements))
        }
      }

      possibleMoves = possibleMoves.tail
    }

    println(foundPaths)
    if (foundPaths.nonEmpty) {
      foundPaths.sortWith((a, b) => a.length < b.length).head.toList
    } else {
      List[Movement]()
    }
  }

  private def canMove(player: Player, map: Array[Array[BoardField]], movement: Movement): Boolean = {
    val p = player.copy()
    p.move(movement)

    if (!isInBounds(p, map, movement)) {
      return false
    }

    val coveredA = map(p.a.y)(p.a.x)
    val coveredB = map(p.b.y)(p.b.x)

    if (p.isUpright) {
      if (coveredA.fieldType == FieldType.SPECIAL) {
        return false
      }
    }

    coveredA.fieldType != FieldType.VOID && coveredB.fieldType != FieldType.VOID
  }

  private def isInBounds(player: Player, map: Array[Array[BoardField]], movement: Movement): Boolean = {
    movement match {
      case UP => player.a.y > 0 && player.b.y > 0
      case DOWN => player.a.y + 1 < map.length && player.b.y + 1 < map.length
      case LEFT => player.a.x > 0 && player.b.x > 0
      case RIGHT => player.a.x + 1 < map(0).length && player.b.x + 1 < map(0).length
    }
  }

  private def notVisited(visitedPos: ArrayBuffer[Player], player: Player, movement: Movement): Boolean = {
    val p = player.copy()
    p.move(movement)

    for (
      position <- visitedPos.indices
    ) {
      if (p.a == visitedPos(position).a && p.b == visitedPos(position).b) {
        return false
      }
    }

    true
  }

  // Runs the recursive version of the solution algorithm to find the first
  // solution path, if any
  def runSolver(map: Array[Array[BoardField]]): List[Movement] = {
    // Find the starting position
    val start = maps.MapsManager.findFieldPosition(map, START)
    val startPlayer = new Player(start)
    val visited: ArrayBuffer[Player] = ArrayBuffer[Player](
      startPlayer
    )
    val possibleMoves: ListBuffer[PlayerMove] = findPossibleMoves(startPlayer, map)

    @tailrec
    def runSolverRec(
                      visited: ArrayBuffer[Player], // Accumulates the visited fields
                      possibleMoves: ListBuffer[PlayerMove] = ListBuffer[PlayerMove](), // Accumulates possible moves
                    ): ListBuffer[Movement] = {
      possibleMoves match {
        // Check if there are no possible moves to do -> if the iteration is at this point, no solutions were found
        case ListBuffer() => ListBuffer[Movement]()
        // Check if the next move will produce a solution
        case ListBuffer(head, tail@_*) =>
          val possibleMove = head
          val p = possibleMove._1.copy()
          val movement = possibleMove._2
          val prevMovements = possibleMove._3.clone()

          if (doneWithMovement(p, map, movement)) {
            // First solution path found, return it
            return prevMovements += movement
          }

          p.move(movement)

          visited += p
          prevMovements += movement

          val seq = ListBuffer.empty ++= tail.toList

          if (canMove(p, map, UP) && notVisited(visited, p, UP)) {
            seq += ((p, UP, prevMovements))
          }

          if (canMove(p, map, DOWN) && notVisited(visited, p, DOWN)) {
            seq += ((p, DOWN, prevMovements))
          }

          if (canMove(p, map, RIGHT) && notVisited(visited, p, RIGHT)) {
            seq += ((p, RIGHT, prevMovements))
          }

          if (canMove(p, map, LEFT) && notVisited(visited, p, LEFT)) {
            seq += ((p, LEFT, prevMovements))
          }

          runSolverRec(visited, seq)
      }
    }

    val foundPaths: ListBuffer[Movement] = runSolverRec(visited, possibleMoves)

    if (foundPaths.nonEmpty) {
      val list = foundPaths.toList
      println(f"\nFound solution: \n${list.mkString(" -> ")}\n")

      list
    } else {
      println("No solution found for the specified map")

      List[Movement]()
    }
  }

  private def doneWithMovement(player: Player, map: Array[Array[BoardField]], movement: Movement): Boolean = {
    val p = player.copy()
    p.move(movement)

    p.isUpright && map(p.a.y)(p.a.x).fieldType == FieldType.TARGET
  }

  private def findPossibleMoves(currentPos: Player, map: Array[Array[BoardField]]): ListBuffer[PlayerMove] = {
    val possibleMoves: ListBuffer[PlayerMove] = ListBuffer[PlayerMove]()

    if (canMove(currentPos, map, UP)) {
      possibleMoves += ((currentPos, UP, ListBuffer[Movement]()))
    }

    if (canMove(currentPos, map, DOWN)) {
      possibleMoves += ((currentPos, DOWN, ListBuffer[Movement]()))
    }

    if (canMove(currentPos, map, RIGHT)) {
      possibleMoves += ((currentPos, RIGHT, ListBuffer[Movement]()))
    }

    if (canMove(currentPos, map, LEFT)) {
      possibleMoves += ((currentPos, LEFT, ListBuffer[Movement]()))
    }

    possibleMoves
  }
}

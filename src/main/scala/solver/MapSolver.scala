package solver

import maps.Movement.{DOWN, LEFT, Movement, RIGHT, UP}
import maps._

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object MapSolver {
  type PossibleMove = (Player, Movement, List[Movement])
  type PossibleMove2 = (Player, List[Movement])

  //  def solveMap(map: Array[Array[BoardField]]): Unit = {
  //    val startingPosition: Position = findStartingPosition(map)
  //
  //    // if solution -> current path
  //    // if no more moves -> empty
  //
  //    // TODO make tail recursive
  //    //        def solve(playerPos: Player, visitedFields: ArrayBuffer[Player], currentPath: ListBuffer[Movement]): List[Movement] = {
  //    //          if (playerPos.isUpright && map(playerPos.a.y)(playerPos.a.x).fieldType == FieldType.TARGET) currentPath.toList
  //    //          else {
  //    //            println(f"Adding visited fields a=${playerPos.a} b=${playerPos.b}")
  //    //            visitedFields += playerPos.copy()
  //    //
  //    //            if (canMove(playerPos, map, UP) && notVisited(visitedFields, playerPos, UP)) {
  //    //              val p = playerPos.copy()
  //    //              p.move(UP)
  //    //
  //    //              solve(p, visitedFields, currentPath += UP)
  //    //            }
  //    //
  //    //            if (canMove(playerPos, map, DOWN) && notVisited(visitedFields, playerPos, DOWN)) {
  //    //              val p = playerPos.copy()
  //    //              p.move(DOWN)
  //    //
  //    //              solve(p, visitedFields, currentPath += DOWN)
  //    //            }
  //    //
  //    //            if (canMove(playerPos, map, LEFT) && notVisited(visitedFields, playerPos, LEFT)) {
  //    //              val p = playerPos.copy()
  //    //              p.move(LEFT)
  //    //
  //    //              solve(p, visitedFields, currentPath += LEFT)
  //    //            }
  //    //
  //    //            if (canMove(playerPos, map, RIGHT) && notVisited(visitedFields, playerPos, RIGHT)) {
  //    //              val p = playerPos.copy()
  //    //              p.move(RIGHT)
  //    //
  //    //              solve(p, visitedFields, currentPath += RIGHT)
  //    //            }
  //    //
  //    //            currentPath.toList
  //    //          }
  //    //        }
  //
  //    def solve(visitedFields: ArrayBuffer[Player], pm: ListBuffer[(Player, List[Movement])], foundSolutions: List[List[Movement]]): List[List[Movement]] = {
  //      pm match {
  //        case ListBuffer() => foundSolutions
  //        case ListBuffer(head, _) if head._1.isUpright && map(head._1.a.y)(head._1.a.x).fieldType == FieldType.TARGET => foundSolutions ::: List(head._2)
  //        case ListBuffer(head, tail@_*) =>
  //          val p = head._1.copy()
  //
  //          if (canMove(p, map, UP) && notVisited(visitedFields, p, UP)) {
  //            val player = head._1.copy()
  //            player.move(UP)
  //
  //            solve(
  //              visitedFields += player.copy(),
  //              pm += (player, head._2 :: List(UP)),
  //              foundSolutions
  //            )
  //          }
  //
  //          if (canMove(p, map, DOWN) && notVisited(visitedFields, p, DOWN)) {
  //            val player = head._1.copy()
  //            player.move(DOWN)
  //
  //            solve(
  //              visitedFields += player.copy(),
  //              pm += ListBuffer(player, head._2 :: List(DOWN)),
  //              foundSolutions
  //            )
  //          }
  //
  //          if (canMove(p, map, LEFT) && notVisited(visitedFields, p, LEFT)) {
  //            val player = head._1.copy()
  //            player.move(LEFT)
  //
  //            solve(
  //              visitedFields += player.copy(),
  //              pm += ListBuffer(player, head._2 :: List(LEFT)),
  //              foundSolutions
  //            )
  //          }
  //
  //          if (canMove(p, map, RIGHT) && notVisited(visitedFields, p, RIGHT)) {
  //            val player = head._1.copy()
  //            player.move(RIGHT)
  //
  //            solve(
  //              visitedFields += player.copy(),
  //              pm += ListBuffer(player, head._2 :: List(RIGHT)),
  //              foundSolutions
  //            )
  //          }
  //
  //          List[List[Movement]]()
  //      }
  //    }
  //
  //
  //    //    def solve(
  //    //               visitedFields: ArrayBuffer[Player],
  //    //               currentPath: List[Movement],
  //    //               possibleMoves: ListBuffer[PossibleMove],
  //    //               foundPaths: List[List[Movement]]
  //    //             ): List[List[Movement]] = {
  //    //      possibleMoves match {
  //    //        // if there are no more movements to do, return an empty solution list
  //    //        case ListBuffer() =>
  //    //          println("RETURNING PATH DUE TO NO MOVES")
  //    //          foundPaths
  //    //        case ListBuffer(head, tail@_*) =>
  //    //          // move player
  //    //          val p = head._1.copy()
  //    //          p.move(head._2)
  //    //
  //    //          println(f"Adding visited fields a=${p.a} b=${p.b} as ${head._2}")
  //    //          visitedFields += p.copy()
  //    //
  //    //          val listBuff: ListBuffer[PossibleMove] = ListBuffer.empty ++= tail
  //    //
  //    //          // if the current movement is a solution, add the current path
  //    //          if (p.isUpright && map(p.a.y)(p.a.x).fieldType == FieldType.TARGET) {
  //    //            println("RETURNING CURRENT PATH")
  //    //            return foundPaths ::: List(head._3)
  //    //          }
  //    //
  //    //          if (canMove(p, map, UP) && notVisited(visitedFields, p, UP)) {
  //    //            listBuff += ((p, UP, currentPath))
  //    //          }
  //    //
  //    //          if (canMove(p, map, DOWN) && notVisited(visitedFields, p, DOWN)) {
  //    //            listBuff += ((p, DOWN, currentPath))
  //    //          }
  //    //
  //    //          if (canMove(p, map, LEFT) && notVisited(visitedFields, p, LEFT)) {
  //    //            listBuff += ((p, LEFT, currentPath))
  //    //          }
  //    //
  //    //          if (canMove(p, map, RIGHT) && notVisited(visitedFields, p, RIGHT)) {
  //    //            listBuff += ((p, RIGHT, currentPath))
  //    //          }
  //    //
  //    //          // check if there are any moves to be made, and if so,
  //    //          // add them to the list of potential moves
  //    //
  //    //          foundPaths ::: solve(visitedFields, currentPath ::: List[Movement](head._2), listBuff, foundPaths)
  //    //      }
  //    //    }
  //
  //    //    @tailrec
  //    //    def solve(
  //    //               possibleMoves: ListBuffer[PossibleMove2],
  //    //               visitedFields: ArrayBuffer[Player],
  //    //               possiblePaths: ListBuffer[Movement]
  //    //             ): List[Movement] = {
  //    //      possibleMoves match {
  //    //        case ListBuffer() => {
  //    //          println("RETURNING EMPTY PATH")
  //    //          List[Movement]()
  //    //        }
  //    //        case ListBuffer(head, tail@_*) =>
  //    //          val p = head._1.copy()
  //    //
  //    //          // if the current movement is a solution, add the current path
  //    //          if (p.isUpright && map(p.a.y)(p.a.x).fieldType == FieldType.TARGET) {
  //    //            println("RETURNING CURRENT PATH")
  //    //            return head._2
  //    //          }
  //    //
  //    //          solve(possibleMoves, visitedFields, possiblePaths)
  //    //      }
  //    //    }
  //
  //    //    val p = new Player(startingPosition)
  //    //    val possibleMoves = ListBuffer[PossibleMove]()
  //    //
  //    //    if (canMove(p, map, UP)) {
  //    //      possibleMoves += ((p, UP, List[Movement]()))
  //    //    }
  //    //
  //    //    if (canMove(p, map, DOWN)) {
  //    //      possibleMoves += ((p, DOWN, List[Movement]()))
  //    //    }
  //    //
  //    //    if (canMove(p, map, RIGHT)) {
  //    //      possibleMoves += ((p, RIGHT, List[Movement]()))
  //    //    }
  //    //
  //    //    if (canMove(p, map, LEFT)) {
  //    //      possibleMoves += ((p, LEFT, List[Movement]()))
  //    //    }
  //    //
  //    //    //    val solution = solve(ArrayBuffer[Player](p), List[Movement](), possibleMoves, List[List[Movement]]())
  //    //    val solution = solve(p, ArrayBuffer[Player](), ListBuffer[Movement]())
  //    //
  //    //    print(solution)
  //
  //    //    if (solution.nonEmpty) {
  //    //      val file = new File("dumbsolution.txt")
  //    //      val bw = new BufferedWriter(new FileWriter(file))
  //    //      for (line <- solution.head) {
  //    //        bw.write(f"${Movement.getMovementCharacter(line)}\n")
  //    //      }
  //    //      bw.close()
  //    //    }
  //  }

  // The current player position, the next move, the list of previous movements
  type PlayerMove = (Player, Movement, ListBuffer[Movement])

  def runSolver(map: Array[Array[BoardField]]): List[Movement] = {
    // Find the starting position
    val start = findStartingPosition(map)
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

    // Check if the player is out of bounds
    val coveredA = map(p.a.y)(p.a.x)
    val coveredB = map(p.a.y)(p.a.x)

    !(coveredA.fieldType == FieldType.VOID) && !(coveredB.fieldType == FieldType.VOID) && !(coveredA.fieldType == FieldType.SPECIAL && player.isUpright)
  }

  private def isInBounds(player: Player, map: Array[Array[BoardField]], movement: Movement): Boolean = {
    movement match {
      case UP => player.a.y > 0 || player.b.y > 0
      case DOWN => player.a.y + 1 < map.length || player.b.y + 1 < map.length
      case LEFT => player.a.x > 0 || player.b.x > 0
      case RIGHT => player.a.x + 1 < map(0).length || player.b.x + 1 < map(0).length
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


  private def findStartingPosition(map: Array[Array[BoardField]]): Position = {
    for (
      row <- map.indices
    ) {
      for (
        column <- map(row).indices
      ) {
        if (map(row)(column).fieldType == FieldType.START) {
          return map(row)(column).position
        }
      }
    }

    // TODO handle this in event loading
    throw new Error("Map is missing a starting point")
  }

}

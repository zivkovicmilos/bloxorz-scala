package maps

object MapDrawer {
  // Outputs the specified map to the console
  def drawMap(map: Array[Array[BoardField]]): Unit = {
    for {
      row <- map.indices
    } {
      for (
        currIndx <- 0 until 3
      ) {
        for {
          column <- map(row).indices
        } {
          // Each row has 3 print passes
          drawFieldPart(currIndx, map(row)(column).getBoardField)
        }
        print('\n')
      }
    }

    print(Console.RESET + '\n')
  }

  // Draws the specified map with the player overlay
  def drawMapWithPlayer(map: Array[Array[BoardField]], playerAlt: Player): Unit = {
    for {
      y <- map.indices
    } {
      for (
        currIndx <- 0 until 3
      ) {
        for {
          x <- map(y).indices
        } {
          // Each row has 3 print passes
          if (playerAlt.a == Position(x, y)) {
            drawFieldPart(currIndx, BoardField(FieldType.BLOCK, playerAlt.a).getBoardField)
          } else if (playerAlt.b == Position(x, y)) {
            drawFieldPart(currIndx, BoardField(FieldType.BLOCK, playerAlt.b).getBoardField)
          } else {
            drawFieldPart(currIndx, map(y)(x).getBoardField)
          }
        }
        print('\n')
      }
    }

    print(Console.RESET + '\n')
  }

  // Draws a section of the field
  private def drawFieldPart(currIndx: Int, fieldArr: Array[Array[String]]): Unit = {
    for {
      innerPtr <- fieldArr(currIndx).indices
    } {
      print(fieldArr(currIndx)(innerPtr))
    }
  }
}

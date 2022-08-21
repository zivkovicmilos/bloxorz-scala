package maps

object MapDrawer {
  // Outputs the specified map to the console
  def drawMap(map: Array[Array[BoardField]]): Unit = {
    for {
      x <- map.indices
    } {
      for (
        currIndx <- 0 until 3
      ) {
        for {
          j <- map(x).indices
        } {
          // Each row has 3 print passes
          val fieldArr = map(x)(j).getBoardField

          for {
            innerPtr <- fieldArr(currIndx).indices
          } {
            print(fieldArr(currIndx)(innerPtr))
          }
        }
        print('\n')
      }
    }

    print(Console.RESET + '\n')
  }
}

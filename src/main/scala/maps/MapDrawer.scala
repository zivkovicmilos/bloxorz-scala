package maps

object MapDrawer {
  def drawMap(map: Array[Array[BoardField]]): Unit = {
    for {
      x <- map.indices
    } {
      var currIndx = 0

      while (currIndx < 3) {
        for {
          j <- map(x).indices
        } {
          // Each row has 3 print passes
          val fieldArr = map(x)(j).getBoardField

          val arr = fieldArr(currIndx)

          for {
            innerPtr <- arr.indices
          } {
            print(fieldArr(currIndx)(innerPtr))
          }
        }
        print('\n')

        currIndx += 1
      }
    }

    print(Console.RESET + '\n')
  }
}

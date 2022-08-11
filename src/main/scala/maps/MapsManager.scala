package maps

import java.io.File
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object MapsManager {
  var loadedMaps: ArrayBuffer[Array[Array[BoardField]]] = ArrayBuffer[Array[Array[BoardField]]]()
  val mapPrefix = "map_"

  def getListOfFiles(dir: File): List[File] = {
    dir.listFiles.filter(_.isFile).toList.filter { file =>
      file.getName.startsWith(mapPrefix)
    }
  }

  def getNumMaps: Int = {
    loadedMaps.length
  }

  def addMap(mapFile: File): Unit = {
    loadedMaps += loadMap(mapFile)
  }

  def loadMap(mapFile: File): Array[Array[BoardField]] = {
    val source = Source.fromFile(mapFile)
    var x = 0
    val result: ArrayBuffer[Array[BoardField]] = ArrayBuffer[Array[BoardField]]()

    // TODO handle malformed files
    for (line <- source.getLines) {
      val row: ArrayBuffer[BoardField] = ArrayBuffer[BoardField]()

      var y = 0
      for (char <- line) {
        row += BoardField(FieldType.getType(char), x, y)

        y += 1
      }

      result += row.toArray
      x += 1
    }

    source.close()

    result.toArray
  }

  def loadMaps(): Unit = {
    val mapFiles = getListOfFiles(
      new File(
        getClass.getResource("/maps").getPath
      )
    )

    if (mapFiles.size < 1) {
      return
    }

    for (
      mapFile <- mapFiles
    ) {
      loadedMaps += loadMap(mapFile)
    }
  }
}

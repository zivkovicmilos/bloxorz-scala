package maps

import java.io.File
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object MapsManager {
  var loadedMaps: ArrayBuffer[Array[Array[BoardField]]] = ArrayBuffer[Array[Array[BoardField]]]()
  val mapPrefix = "map_"
  val mapFiles: List[String] = List[String](
    "map_1.txt"
  )

  def getListOfFiles(dir: File): List[File] = {
    dir.listFiles.filter(_.isFile).toList.filter { file =>
      file.getName.startsWith(mapPrefix)
    }
  }

  def getNumMaps: Int = {
    loadedMaps.length
  }

  def addMap(mapFile: String): Unit = {
    loadedMaps += loadMap(mapFile)
  }

  def loadMap(mapFile: String): Array[Array[BoardField]] = {
    val resource = Source.fromResource(mapFile)

    var x = 0
    val result: ArrayBuffer[Array[BoardField]] = ArrayBuffer[Array[BoardField]]()

    // TODO handle malformed files
    for (line <- resource.getLines) {
      val row: ArrayBuffer[BoardField] = ArrayBuffer[BoardField]()

      var y = 0
      for (char <- line) {
        row += BoardField(FieldType.getType(char), x, y)

        y += 1
      }

      result += row.toArray
      x += 1
    }

    resource.close()

    result.toArray
  }

  def loadMaps(): Unit = {
    // TODO handle map loading errors

    for (
      mapFile <- mapFiles
    ) {
      loadedMaps += loadMap(mapFile)
    }
  }

  def getMap(index: Int): Array[Array[BoardField]] = {
    if (index > loadedMaps.length) {
      throw new Error("invalid map selected")
    }

    loadedMaps(index - 1)
  }
}

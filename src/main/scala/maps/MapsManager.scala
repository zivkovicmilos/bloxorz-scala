package maps

import maps.FieldType.{FieldType, START, TARGET}

import java.io.File
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.{BufferedSource, Source}

object MapsManager {
  val loadedMaps: ArrayBuffer[Array[Array[BoardField]]] = ArrayBuffer[Array[Array[BoardField]]]()
  val mapPrefix = "map_"
  val mapFiles: List[String] = List[String](
    "map_1.txt",
    "map_2.txt",
    "map_3.txt",
    "map_4.txt",
    "map_5.txt"
  )

  // Returns the formatted map names
  def getMapNames: List[String] = {
    val menuItems: ListBuffer[String] = ListBuffer[String]()

    for (
      i <- 1 to MapsManager.getNumMaps
    ) {
      menuItems += "Map %d".format(i)
    }

    menuItems.toList
  }

  // Returns the number of loaded maps
  def getNumMaps: Int = {
    loadedMaps.length
  }

  // Loads in a map from the specified file
  def addMapFromFile(path: String): Unit = {
    val resource = getFileSource(path)
    loadedMaps += loadMap(resource)

    resource.close()
  }

  private def getFileSource(path: String): BufferedSource = {
    val file = new File(path)

    if (!file.isFile || !file.exists()) {
      throw new Error(f"File $path is not a valid file path")
    }

    Source.fromFile(file)
  }

  // Returns the map from the specified file
  private def loadMap(resource: BufferedSource): Array[Array[BoardField]] = {
    var y = 0
    val result: ArrayBuffer[Array[BoardField]] = ArrayBuffer[Array[BoardField]]()

    for (line <- resource.getLines) {
      val row: ArrayBuffer[BoardField] = ArrayBuffer[BoardField]()

      var x = 0
      for (char <- line) {
        row += BoardField(FieldType.getType(char), Position(x, y))

        x += 1
      }

      result += row.toArray
      y += 1
    }

    val loadedMap = result.toArray

    if (!isValidMap(loadedMap)) {
      throw new Error("Map has an invalid format")
    }

    loadedMap
  }

  // Validates that the map is valid (has a start and target position)
  def isValidMap(map: Array[Array[BoardField]]): Boolean = {
    // Make sure the map has a start and target position
    val start = findFieldPosition(map, START)
    val target = findFieldPosition(map, TARGET)

    (start != target) && (start != Position(-1, -1)) && (target != Position(-1, -1))
  }

  // Returns the location of the specified field position on the map
  def findFieldPosition(map: Array[Array[BoardField]], fieldType: FieldType): Position = {
    for (
      row <- map.indices
    ) {
      for (
        column <- map(row).indices
      ) {
        if (map(row)(column).fieldType == fieldType) {
          return map(row)(column).position
        }
      }
    }

    Position(-1, -1)
  }

  // Loads all of the predefined maps from
  // the resources folder
  def loadMaps(): Unit = {
    for (
      mapFile <- mapFiles
    ) {
      try {
        loadedMaps += loadMapFromResource(mapFile)
      } catch {
        case e: Error => println(f"Unable to load map file $mapFile: ${e.getMessage}")
      }
    }
  }

  private def loadMapFromResource(mapName: String): Array[Array[BoardField]] = {
    val resource = Source.fromResource(mapName)
    val map = loadMap(resource)

    resource.close()
    map
  }

  // Fetches a preloaded map, if present
  def getMap(index: Int): Array[Array[BoardField]] = {
    if (index > loadedMaps.length) {
      throw new Error("invalid map selected")
    }

    loadedMaps(index - 1).map(_.clone)
  }

  // Adds the specified map to a list of loaded maps
  def appendMap(map: Array[Array[BoardField]]): Unit = {
    loadedMaps += map
  }
}

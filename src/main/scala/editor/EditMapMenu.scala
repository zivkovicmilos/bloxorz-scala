package editor

import maps.FieldType.{FieldType, GROUND, SPECIAL, START, TARGET, VOID}
import maps.{BoardField, FieldType, MapsManager, Position}
import menu.MenuPrinter

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable.ListBuffer

class EditMapMenu(map: Array[Array[BoardField]]) extends menu.Menu {
  var feedback: String = ""

  // TODO add save option
  val menuItems: ListBuffer[String] = ListBuffer[String](
    "Save Modified Map",
    EditOperation.getOperationDescription(EditOperation.REMOVE_TILE),
    EditOperation.getOperationDescription(EditOperation.ADD_TILE),
    EditOperation.getOperationDescription(EditOperation.SWAP_WITH_SPECIAL),
    EditOperation.getOperationDescription(EditOperation.SWAP_WITH_REGULAR),
    EditOperation.getOperationDescription(EditOperation.SET_START),
    EditOperation.getOperationDescription(EditOperation.SET_TARGET),
    EditOperation.getOperationDescription(EditOperation.INVERT),
    EditOperation.getOperationDescription(EditOperation.SWAP_ALL_SPECIAL),
    EditOperation.getOperationDescription(EditOperation.FILTER_SPECIAL),
    // TODO add support for creating new operation sequences
    // TODO add support for creating a composite operation from swap + invert + composites
  )

  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Edit Selected Map",
      menuItems.toList
    )
    if (feedback != "") {
      println(feedback)
    }
  }

  override def handleInput(input: String): Unit = {
    feedback = ""

    try {
      input match {
        // TODO handle this better
        case "back" => menu.MenuSwitcher.goBack()
        case "1" => saveMap()
        case "2" => convertTile(GROUND, VOID)
        case "3" => convertTile(VOID, GROUND)
        case "4" => swapFields(GROUND, SPECIAL)
        case "5" => swapFields(SPECIAL, GROUND)
        case "6" => swapFieldWithGround(START)
        case "7" => swapFieldWithGround(TARGET)
        case "8" => swapStartAndTarget()
        case "9" => swapAllSpecial()
        case "10" => filterSpecial()
      }
    } catch {
      case e: Error => feedback = f"Error occurred during map edits: $e"
    }
  }

  private def saveMap(): Unit = {
    // Save the map to the Maps Manager
    MapsManager.loadMap(map)

    // Get the total number of maps
    val totalMaps = MapsManager.getNumMaps

    val mapName = f"${MapsManager.mapPrefix}$totalMaps.txt"

    val file = new File(mapName)
    val bw = new BufferedWriter(new FileWriter(file))

    for (
      row <- map.indices
    ) {
      for (
        column <- map(0).indices
      ) {
        val field = map(row)(column)

        bw.write(FieldType.getValue(field.fieldType))
      }
    }

    bw.close()

    feedback = f"Map saved as $mapName"
  }

  private def filterSpecial(): Unit = {
    val position = getAndVerifyCoordinates()

    print("N distance value: ")
    val nValue = scala.io.StdIn.readLine()

    // Make sure the numbers are valid and in bounds
    if (!nValue.forall(Character.isDigit)) {
      throw new Error("Invalid N value specified!")
    }

    val N = nValue.toInt

    // Get all the special candidates
    // Define the check function
    val checkFn = (field: BoardField) => isFieldType(field.position, SPECIAL)

    // Get all special candidates
    val specialCandidates = getFieldCandidates(checkFn)

    val filterAllowed = (fieldType: FieldType) => fieldType != START && fieldType != TARGET

    for (
      field <- specialCandidates
    ) {
      // Check if the special field is within the same row
      if (field.position.isSameRow(position) && field.position.rowDistance(position) <= N && filterAllowed(field.fieldType)) {
        setBoardField(position, GROUND)

        feedback = f"Tile at position ${position.x}, ${position.y} swapped with $GROUND"

        return
      }

      // Check if the special field is within the same column
      if (field.position.isSameColumn(position) && field.position.columnDistance(position) <= N && filterAllowed(field.fieldType)) {
        setBoardField(position, GROUND)

        feedback = f"Tile at position ${position.x}, ${position.y} swapped with $GROUND"

        return
      }
    }

    feedback = "No tiles filtered"
  }

  private def swapFields(initialType: FieldType, desiredType: FieldType): Unit = {
    val position = getAndVerifyCoordinates()

    // Check if the given field is of the initial type
    if (!isFieldType(position, initialType)) {
      throw new Error(f"Selected tile is not a ${initialType} field!")
    }

    setBoardField(position, desiredType)

    feedback = f"Tile at position ${position.x}, ${position.y} set to $desiredType"
  }

  private def swapAllSpecial(): Unit = {
    // Define the check function
    val checkFn = (field: BoardField) => isFieldType(field.position, SPECIAL)

    // Get all special candidates
    val specialCandidates = getFieldCandidates(checkFn)

    for (
      field <- specialCandidates
    ) {
      setBoardField(field.position, GROUND)
    }

    feedback = f"$SPECIAL tiles swapped with $GROUND"
  }

  private def swapStartAndTarget(): Unit = {
    // Define the check function
    val checkFnStart = (field: BoardField) => isFieldType(field.position, START)
    val checkFnTarget = (field: BoardField) => isFieldType(field.position, TARGET)

    // It should be assumed that the fields exist
    val initialStart = getFieldCandidates(checkFnStart).head.position
    val initialTarget = getFieldCandidates(checkFnTarget).head.position

    setBoardField(initialStart, TARGET)
    setBoardField(initialTarget, START)

    feedback = f"$START and $TARGET positions swapped"

  }

  private def swapFieldWithGround(fieldType: FieldType): Unit = {
    val position = getAndVerifyCoordinates()

    // Define the check function
    val checkFn = (field: BoardField) => isFieldType(field.position, fieldType)

    // It should be assumed that the field exists
    val initialField = getFieldCandidates(checkFn).head.position

    setBoardField(position, fieldType)
    setBoardField(initialField, GROUND)

    feedback = f"Tile at position ${position.x}, ${position.y} set to $GROUND"
  }

  private def convertTile(removeType: FieldType, addType: FieldType): Unit = {
    // Define the check function
    val checkFn = (field: BoardField) => isEdge(field) && isFieldType(field.position, removeType)

    // Check if there are multiple candidates for removing
    val candidates = getFieldCandidates(checkFn)

    if (candidates.isEmpty) {
      throw new Error("No possible tile candidates found")
    }

    if (candidates.length == 1) {
      // There is a single tile that can be added, add it
      val tile = candidates.head

      setBoardField(tile.position, addType)

      feedback = f"Tile at field ${tile.position.x}, ${tile.position.y} set to $addType"

      return
    }

    val position = getAndVerifyCoordinates()

    // Make sure the tile at these coordinates is a valid tile
    if (!candidates.contains(BoardField(removeType, position))) {
      throw new Error("Selected field is not an edge tile!")
    }

    setBoardField(position, addType)
  }

  private def getAndVerifyCoordinates(): Position = {
    // Wait for the user's input
    print("Tile (x):")
    val x = scala.io.StdIn.readLine()
    print("Tile(y):")
    val y = scala.io.StdIn.readLine()

    // Make sure the numbers are valid and in bounds
    if (!x.forall(Character.isDigit) || !y.forall(Character.isDigit)) {
      throw new Error("Invalid coordinates specified!")
    }

    val position = Position(x.toInt, y.toInt)

    if (outOfBounds(position)) {
      throw new Error("Coordinates are out of bounds!")
    }

    position
  }


  private def setBoardField(position: Position, fieldType: FieldType): Unit = {
    map(position.y)(position.x) = BoardField(fieldType, position)
  }

  private def getFieldCandidates(checkFn: BoardField => Boolean): List[BoardField] = {
    val candidates = ListBuffer[BoardField]()
    for (
      row <- map.indices
    ) {
      for (
        column <- map.indices
      ) {
        val field = map(row)(column)

        if (checkFn(field)) {
          candidates += field
        }
      }
    }

    candidates.toList
  }

  private def isEdge(field: BoardField): Boolean = {
    // A field is an edge field if it has any void fields as neighbors
    // or if it is a field at the far edges of the map
    isBorderField(field) || hasVoidNeighbors(field)
  }

  private def isFieldType(position: Position, fieldType: FieldType): Boolean = {
    map(position.y)(position.x).fieldType == fieldType
  }

  private def hasVoidNeighbors(field: BoardField): Boolean = {
    val leftNeighbor = Position(field.position.x - 1, field.position.y)
    val rightNeighbor = Position(field.position.x + 1, field.position.y)
    val upNeighbor = Position(field.position.x, field.position.y - 1)
    val downNeighbor = Position(field.position.x, field.position.y + 1)

    if (!outOfBounds(leftNeighbor) && isVoidField(leftNeighbor)) {
      return true
    }

    if (!outOfBounds(rightNeighbor) && isVoidField(rightNeighbor)) {
      return true
    }

    if (!outOfBounds(upNeighbor) && isVoidField(upNeighbor)) {
      return true
    }

    outOfBounds(downNeighbor) && isVoidField(downNeighbor)
  }

  private def isBorderField(field: BoardField): Boolean = {
    field.position.x == 0 || field.position.y == 0 ||
      field.position.y + 1 == map.length || field.position.x + 1 == map(0).length
  }

  private def isVoidField(position: Position): Boolean = {
    isFieldType(position, VOID)
  }

  private def outOfBounds(position: Position): Boolean = {
    if (position.x < 0 || position.y < 0) {
      return true
    }

    position.x >= map(0).length || position.y >= map.length
  }
}

package editor

import editor.DefaultOperation.{ADD_TILE, DEFINE_OPERATION_LIST, FILTER_SPECIAL, INVERT, REMOVE_TILE, SAVE_MAP, SET_START, SET_TARGET, SWAP_ALL_SPECIAL, SWAP_WITH_REGULAR, SWAP_WITH_SPECIAL}
import maps.FieldType.{FieldType, GROUND, SPECIAL, START, TARGET, VOID}
import maps._
import menu.MenuPrinter

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable.ListBuffer

class EditMapMenu(map: Array[Array[BoardField]]) extends menu.Menu {
  val availableOperations: ListBuffer[EditOperation] = ListBuffer[EditOperation](
    // Define basic operations
    new EditOperation {
      override def run(): Unit = {
        saveMap()
      }

      override def getName: String = DefaultOperation.getName(SAVE_MAP)
    },
    new EditOperation {
      override def run(): Unit = {
        convertTile(GROUND, VOID)
      }

      override def getName: String = DefaultOperation.getName(REMOVE_TILE)
    },
    new EditOperation {
      override def run(): Unit = {
        addTileToEdge()
      }

      override def getName: String = DefaultOperation.getName(ADD_TILE)
    },
    new EditOperation {
      override def run(): Unit = {
        swapFields(GROUND, SPECIAL)
      }

      override def getName: String = DefaultOperation.getName(SWAP_WITH_SPECIAL)
    },
    new EditOperation {
      override def run(): Unit = {
        swapFields(SPECIAL, GROUND)
      }

      override def getName: String = DefaultOperation.getName(SWAP_WITH_REGULAR)
    },
    new EditOperation {
      override def run(): Unit = {
        swapFieldWithGround(START)
      }

      override def getName: String = DefaultOperation.getName(SET_START)
    },
    new EditOperation {
      override def run(): Unit = {
        swapFieldWithGround(TARGET)
      }

      override def getName: String = DefaultOperation.getName(SET_TARGET)
    },
    new EditOperation {
      override def run(): Unit = {
        swapStartAndTarget()
      }

      override def getName: String = DefaultOperation.getName(INVERT)
    },
    new EditOperation {
      override def run(): Unit = {
        swapAllSpecial()
      }

      override def getName: String = DefaultOperation.getName(SWAP_ALL_SPECIAL)
    },
    new EditOperation {
      override def run(): Unit = {
        filterSpecial()
      }

      override def getName: String = DefaultOperation.getName(FILTER_SPECIAL)
    },
    new EditOperation {
      override def run(): Unit = {
        defineOperationList()
      }

      override def getName: String = DefaultOperation.getName(DEFINE_OPERATION_LIST)
    }
  )
  var feedback: String = ""

  override def display(): Unit = {
    MapDrawer.drawMap(map)

    val menuItems = for (operation <- availableOperations) yield operation.getName

    menuItems += "Back"

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
        case x if x.forall(Character.isDigit) && x.toInt == availableOperations.size + 1 => menu.MenuSwitcher.goBack()
        case n =>
          if (n.forall(Character.isDigit) && n.toInt <= availableOperations.size) {
            val selectedOp = n.toInt

            availableOperations(selectedOp - 1).run()
          }
      }
    } catch {
      case e: Error => feedback = f"Error occurred during map edits: ${e.getMessage}"
    }
  }

  private def defineOperationList(): Unit = {
    print("New list name: ")
    val listName = scala.io.StdIn.readLine()

    print("Desired operations in list (format: x y z...): ")
    val requestedOperations = scala.io.StdIn.readLine()

    val operations = requestedOperations.split(" ")
    val operationsList = ListBuffer[EditOperation]()

    for (
      requestedOp <- operations
    ) {
      if (!requestedOp.forall(Character.isDigit)) {
        throw new Error("Invalid requested operation value specified!")
      }

      val opInt = requestedOp.toInt

      if (opInt > availableOperations.size) {
        throw new Error("Invalid operation selected")
      }

      operationsList += availableOperations(opInt - 1)
    }

    availableOperations += new EditOperation {
      override def run(): Unit = {
        for (
          op <- operationsList
        ) {
          op.run()
        }
      }

      override def getName: String = listName
    }

    feedback = f"Created a new operation list: $listName"
  }

  private def saveMap(): Unit = {
    // Save the map to the Maps Manager
    MapsManager.appendMap(map)

    // Get the total number of maps
    val totalMaps = MapsManager.getNumMaps

    val mapName = f"${MapsManager.mapPrefix}$totalMaps.txt"

    val file = new File(mapName)
    val bw = new BufferedWriter(new FileWriter(file))

    for (
      row <- map.indices
    ) {
      for (
        column <- map(row).indices
      ) {
        val field = map(row)(column)

        bw.write(FieldType.getValue(field.fieldType))
      }

      bw.write('\n')
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

    feedback = f"Tile at position ${position.x}, ${position.y} set to $fieldType"
  }

  private def addTileToEdge(): Unit = {
    val position = getAndVerifyCoordinates()

    // Check if the field is right
    if (!isFieldType(position, VOID)) {
      throw new Error(f"Selected field is not of $VOID type")
    }

    // Check if any neighbors are edge tiles

    // Find all regular edge-ground tiles
    // Define the check function
    val checkFn = (field: BoardField) => isEdge(field) && isFieldType(field.position, GROUND)

    // Check if there are multiple candidates for removing
    val candidates = getFieldCandidates(checkFn)

    if (candidates.isEmpty) {
      throw new Error("No possible ground tile candidates found")
    }

    if (!neighborsInsSet(position, candidates)) {
      throw new Error("No possible void tile candidates found")
    }

    setBoardField(position, GROUND)
  }

  private def neighborsInsSet(position: Position, set: List[BoardField]): Boolean = {
    val leftNeighbor = Position(position.x - 1, position.y)
    val rightNeighbor = Position(position.x + 1, position.y)
    val upNeighbor = Position(position.x, position.y - 1)
    val downNeighbor = Position(position.x, position.y + 1)

    for (
      neighbor <- set
    ) {
      if (neighbor.position == leftNeighbor || neighbor.position == rightNeighbor || neighbor.position == upNeighbor || neighbor.position == downNeighbor) {
        return true
      }
    }

    false
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

  private def setBoardField(position: Position, fieldType: FieldType): Unit = {
    map(position.y)(position.x) = BoardField(fieldType, position)
  }

  private def getFieldCandidates(checkFn: BoardField => Boolean): List[BoardField] = {
    val candidates = ListBuffer[BoardField]()
    for (
      row <- map.indices
    ) {
      for (
        column <- map(row).indices
      ) {
        val field = map(row)(column)

        if (checkFn(field)) {
          candidates += field
        }
      }
    }

    candidates.toList
  }

  private def getAndVerifyCoordinates(): Position = {
    // Wait for the user's input
    print("Tile (x): ")
    val x = scala.io.StdIn.readLine()
    print("Tile (y): ")
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

  private def outOfBounds(position: Position): Boolean = {
    if (position.x < 0 || position.y < 0) {
      return true
    }

    position.x >= map(0).length || position.y >= map.length
  }

  private def isEdge(field: BoardField): Boolean = {
    // A field is an edge field if it has any void fields as neighbors
    // or if it is a field at the far edges of the map
    isBorderField(field) || hasVoidNeighbors(field)
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

  private def isVoidField(position: Position): Boolean = {
    isFieldType(position, VOID)
  }

  private def isFieldType(position: Position, fieldType: FieldType): Boolean = {
    map(position.y)(position.x).fieldType == fieldType
  }

  private def isBorderField(field: BoardField): Boolean = {
    field.position.x == 0 || field.position.y == 0 ||
      field.position.y + 1 == map.length || field.position.x + 1 == map(0).length
  }
}

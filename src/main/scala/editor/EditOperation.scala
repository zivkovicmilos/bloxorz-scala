package editor

// TODO make this a trait
// Defines the different types of player movements
object EditOperation extends Enumeration {
  type EditOperation = Value

  val
  REMOVE_TILE,
  ADD_TILE,
  SWAP_WITH_SPECIAL,
  SWAP_WITH_REGULAR,
  SET_START,
  SET_TARGET,
  INVERT,
  SWAP_ALL_SPECIAL,
  FILTER_SPECIAL
  // TODO define the rest
  = Value


  def getEditOperation(operation: String): EditOperation = {
    operation match {
      case "Remove tile from the edge" => REMOVE_TILE
      case "Add tile to the edge" => ADD_TILE
      case "Make a normal tile special" => SWAP_WITH_SPECIAL
      case "Make a special tile normal" => SWAP_WITH_REGULAR
      case "Set start position" => SET_START
      case "Set target position" => SET_TARGET
      case "Invert start and target" => INVERT
      case "Swap special tiles" => SWAP_ALL_SPECIAL
      case "Filter special tiles" => FILTER_SPECIAL
      case _ => throw new Error("Unknown move")
    }
  }

  def getOperationDescription(operation: EditOperation): String = {
    operation match {
      case REMOVE_TILE => "Remove tile from the edge"
      case ADD_TILE => "Add tile to the edge"
      case SWAP_WITH_SPECIAL => "Make a normal tile special"
      case SWAP_WITH_REGULAR => "Make a special tile normal"
      case SET_START => "Set start position"
      case SET_TARGET => "Set target position"
      case INVERT => "Invert start and target"
      case SWAP_ALL_SPECIAL => "Swap special tiles"
      case FILTER_SPECIAL => "Filter special tiles"
      case _ => throw new Error("Unknown move")
    }
  }
}

package editor

// TODO make this a trait
// Defines the different types of player movements
object DefaultOperation extends Enumeration {
  type DefaultOperation = Value

  val
  SAVE_MAP,
  DEFINE_OPERATION_LIST,
  REMOVE_TILE,
  ADD_TILE,
  SWAP_WITH_SPECIAL,
  SWAP_WITH_REGULAR,
  SET_START,
  SET_TARGET,
  INVERT,
  SWAP_ALL_SPECIAL,
  FILTER_SPECIAL
  = Value

  def getName(operation: DefaultOperation): String = {
    operation match {
      case SAVE_MAP => "Save map"
      case DEFINE_OPERATION_LIST => "Define op. list"
      case REMOVE_TILE => "Remove tile (edge)"
      case ADD_TILE => "Add tile (ground)"
      case SWAP_WITH_SPECIAL => "Ground -> Special"
      case SWAP_WITH_REGULAR => "Special -> Ground"
      case SET_START => "Set start"
      case SET_TARGET => "Set target"
      case INVERT => "Start <-> Target"
      case SWAP_ALL_SPECIAL => "Swap special tiles"
      case FILTER_SPECIAL => "Filter"
      case _ => throw new Error("Unknown operation")
    }
  }
}

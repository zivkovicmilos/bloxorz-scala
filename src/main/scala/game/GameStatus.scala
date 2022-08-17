package game

// Defines the different types of game statuses
object GameStatus extends Enumeration {
  type GameStatus = Value

  val
  SUCCESS,
  FAILURE,
  ONGOING
  = Value
}

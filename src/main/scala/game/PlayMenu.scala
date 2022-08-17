package game

import maps.Movement

class PlayMenu(map: maps.Map) extends menu.Menu {

  override def display(): Unit = {
    map.drawMap()

    println(f"Status is ${map.getGameStatus.toString}")
    // TODO check if the game is over
    if (map.getGameStatus == GameStatus.SUCCESS) {
      println("Game is successfully finished!")
    }

    if (map.getGameStatus == GameStatus.FAILURE) {
      println("Game failed!")
    }

    // TODO display a message if the game is over
  }

  override def handleInput(input: String): Unit = {
    input match {
      // TODO make this better
      case "u" => map.movePlayer(Movement.UP)
      case "d" => map.movePlayer(Movement.DOWN)
      case "l" => map.movePlayer(Movement.LEFT)
      case "r" => map.movePlayer(Movement.RIGHT)
      case _ =>
    }

    // TODO handle only back input if the game is over
  }
}

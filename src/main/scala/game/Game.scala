package game

import game.GameStatus.GameStatus
import maps.BoardField
import maps.Movement.Movement

class Game(map: Array[Array[BoardField]]) {
  class Player(x: Int, y: Int) {
    def move(move: Movement): Unit = {
      // TODO define movements
    }
  }

  var status: GameStatus = GameStatus.ONGOING

}

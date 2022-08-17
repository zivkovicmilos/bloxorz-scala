package game

import maps.MapsManager
import menu.MenuPrinter

object PlayMapMenu extends menu.Menu {
  private var selectedMap = 0;

  def setSelectedMap(map: Int): Unit = {
    selectedMap = map
  }

  override def display(): Unit = {
    MenuPrinter.printMenu(
      f"Map ${selectedMap}",
      List[String](
        "Play with keyboard",
        "Play with file",
        "Back"
      )
    )

  }

  override def handleInput(input: String): Unit = {
    input match {
      case "1" => {
        val map = new maps.Map(MapsManager.getMap(selectedMap))
        map.initializePlayer()

        menu.MenuSwitcher.goForward(new PlayMenu(map))
      }
      case "2" =>
      case "3" => menu.MenuSwitcher.goBack()
      case _ =>
    }
  }
}

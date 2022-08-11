import maps.MapsManager
import menu.MenuSwitcher

object Main extends App {
  var shouldExit = false

  // Initialize present maps
  MapsManager.loadMaps()

  while (!shouldExit) {
    shouldExit = MenuSwitcher.showMenu()
  }
}
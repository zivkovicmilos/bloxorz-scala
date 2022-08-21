import maps.MapsManager
import menu.MenuSwitcher

object Main extends App {
  // Initialize predefined maps
  MapsManager.loadMaps()

  while (!MenuSwitcher.showMenu()) {}
}
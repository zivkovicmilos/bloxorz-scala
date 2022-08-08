import menu.MenuSwitcher

object Main extends App {
  var shouldExit = false

  while (!shouldExit) {
    shouldExit = MenuSwitcher.showMenu()
  }
}
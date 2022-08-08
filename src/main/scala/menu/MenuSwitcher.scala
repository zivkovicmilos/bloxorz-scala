package menu

import scala.collection.{mutable => m}

object MenuSwitcher {
  var menuStack: m.Stack[Menu] = m.Stack[Menu](new BaseMenu())

  def clearConsole(): Unit = {
    print("\u001b[2J")
  }

  def addMenu(menu: Menu): Unit = {
    menuStack.push(menu)
  }

  def showMenu(): Boolean = {
    // Clear the console
    clearConsole()

    // Display the menu
    val currentMenu = menuStack.top
    currentMenu.display()

    // Wait for user input
    if (!currentMenu.handleInput(scala.io.StdIn.readLine())) menuStack.pop()

    menuStack.size < 1
  }
}

package menu

import scala.collection.{mutable => m}

object MenuSwitcher {
  var menuStack: m.Stack[Menu] = m.Stack[Menu](BaseMenu)

  private def clearConsole(): Unit = {
    print("\u001b[2J")
  }

  def goForward(menu: Menu): Unit = {
    menuStack.push(menu)
  }

  def goBack(): Unit = {
    menuStack.pop()
  }

  def showMenu(): Boolean = {
    // Clear the console
    clearConsole()

    // Display the menu
    val currentMenu = menuStack.top
    currentMenu.display()

    // Wait for user input
    print("> ")
    currentMenu.handleInput(scala.io.StdIn.readLine())

    menuStack.size < 1
  }
}

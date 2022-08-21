package menu

import scala.collection.{mutable => m}

object MenuSwitcher {
  val menuStack: m.Stack[Menu] = m.Stack[Menu](BaseMenu)

  // Adds a new menu to the stack
  def goForward(menu: Menu): Unit = {
    menuStack.push(menu)
  }

  // Removes a menu from the stack
  def goBack(): Unit = {
    menuStack.pop()
  }

  // Displays the menu that's at the top of the stack
  def showMenu(): Boolean = {
    // Clear the console
    clearConsole()

    // Display the menu
    val currentMenu = menuStack.top
    currentMenu.display()

    // Wait for user input
    print("> ")
    currentMenu.handleInput(scala.io.StdIn.readLine())

    // If there aren't any more menus to display, exit
    numMenus() < 1
  }

  // Clears the console window
  private def clearConsole(): Unit = {
    print("\u001b[2J")
  }

  // Returns the current number of active menus
  def numMenus(): Int = {
    menuStack.size
  }
}

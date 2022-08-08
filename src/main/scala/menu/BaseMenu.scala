package menu

class BaseMenu extends Menu {
  override def display(): Unit = {
    MenuPrinter.printMenu(
      "Base Menu",
      List[String](
        "Start game",
        "Maps",
        "Solver",
        "Exit"
      )
    )
  }

  override def handleInput(input: String): Boolean = {
    println("The chosen option is " + input)

    input != "4"
  }
}

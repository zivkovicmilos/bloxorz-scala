package menu

// Menu is the base trait all menu implementations
// need to have
trait Menu {
  // Displays the specific menu view
  def display(): Unit

  // Handles the user input
  def handleInput(input: String): Boolean
}
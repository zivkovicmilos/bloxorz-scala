import menu.MenuSwitcher
import org.scalatest.funsuite.AnyFunSuite
import solver.SolverMenu

class MenuSuite extends AnyFunSuite {
  test("should add a new menu to the stack") {
    val initialMenus = MenuSwitcher.numMenus()
    MenuSwitcher.goForward(SolverMenu)

    assert(MenuSwitcher.numMenus() == initialMenus + 1)
  }

  test("should pop a menu from the stack") {
    val initialMenus = MenuSwitcher.numMenus()
    MenuSwitcher.goBack()

    assert(MenuSwitcher.numMenus() == initialMenus - 1)
  }
}
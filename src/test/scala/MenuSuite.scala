import menu.MenuSwitcher
import org.scalatest.funsuite.AnyFunSuite
import solver.SolverMenu

class MenuSuite extends AnyFunSuite {
  test("should add a new menu to the stack") {
    assert(MenuSwitcher.numMenus() == 1)

    MenuSwitcher.goForward(SolverMenu)

    assert(MenuSwitcher.numMenus() == 2)
  }

  test("should pop a menu from the stack") {
    assert(MenuSwitcher.numMenus() == 1)

    MenuSwitcher.goBack()

    assert(MenuSwitcher.numMenus() == 0)
  }
}
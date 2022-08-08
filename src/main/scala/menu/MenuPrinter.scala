package menu

object MenuPrinter {
  val baseWidth = 25 // chars

  // Formats the menu title
  private def getFormattedTitle(title: String): String = {
    val leftover = baseWidth - title.length - 2 // 2 to account for *

    var right = leftover / 2
    if (leftover % 2 != 0) {
      // Make sure the right side has an additional character
      right += 1
    }

    "*" + "".padTo(leftover / 2, ' ') + title + "".padTo(right, ' ') + "*"
  }

  // Formats the menu item
  private def getFormattedItem(item: String): String = {
    "*" + item + "".padTo(baseWidth - item.length - 2, ' ') + "*"
  }

  def printMenu(title: String, menuItems: List[String]): Unit = {
    println("* * * * * * * * * * * * *")
    printf("%s\n", getFormattedTitle(title))
    println("* * * * * * * * * * * * *")
    println("*                       *")

    var indx = 1
    for (menuItem <- menuItems) {
      printf("%s\n", getFormattedItem(" %d. %s".format(indx, menuItem)))
      indx += 1
    }

    println("*                       *")
    println("* * * * * * * * * * * * *")
  }
}

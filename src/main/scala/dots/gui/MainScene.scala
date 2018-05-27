package dots.gui


import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, Priority, VBox}
import scalafx.scene.paint.Color

object MainScene extends Scene(1000, 800) {

  val SPACING = 10

  lazy val playerAColor = Color.web("RED", 1)
  lazy val playerBColor = Color.web("BLUE", 1)

  private var board = new Board(10, 10) {
    hgrow = Priority.Always
    vgrow = Priority.Always
  }


  private val parent = new HBox(SPACING) {
    padding = Insets(SPACING)
    children = Seq(
      new VBox(SPACING) {
        padding = Insets(20)
        children = Seq(
          new Separator,
        )
      },
      new Separator {
        orientation = Orientation.Vertical
      },
      board
    )
  }

  root = parent

  def setBoardSize(rows: Int, columns: Int): Unit = {
    parent.children.remove(board)

    board = new Board(rows, columns) {
      hgrow = Priority.Always
      vgrow = Priority.Always
    }

    parent.children.add(board)

  }

}

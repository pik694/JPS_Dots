package dots.gui


import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, Priority, VBox}
import scalafx.scene.paint.Color

object MainScene extends Scene(1000, 800) {

  val SPACING = 10

  val playerAColor = Color.web("RED", 1)
  val playerBColor = Color.web("BLUE", 1)



  private var board = new BoardView(10, 10) {
    hgrow = Priority.Always
    vgrow = Priority.Always
  }


  private val parent = new HBox(SPACING) {
    padding = Insets(SPACING)
    children = Seq(
      ScorePane,
      new Separator {
        orientation = Orientation.Vertical
      },
      board
    )
  }

  root = parent

  def setBoardSize(rows: Int, columns: Int): Unit = {
    parent.children.remove(board)

    board = new BoardView(rows, columns) {
      hgrow = Priority.Always
      vgrow = Priority.Always
    }

    parent.children.add(board)

  }

}

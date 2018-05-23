package dots.gui

import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Line}

import scala.annotation.tailrec


class Board(val rows: Int, val columns: Int) extends Pane {
  private val STROKE_WIDTH: Double = 2

  private def generateLines: Seq[Line] = {

    @tailrec
    def generateRows(index: Int, seq: Seq[Line]): Seq[Line] = {
      index < rows match {
        case true => generateRows(
          index + 1,
          seq :+ new Line {
            strokeWidth() = STROKE_WIDTH
            startY <== (Board.this.height / rows) * index
            endY <== (Board.this.height / rows) * index
            startX = 0
            endX <== Board.this.width
          }
        )
        case false => seq
      }
    }

    @tailrec
    def generateColumns(index: Int, seq: Seq[Line]): Seq[Line] = {
      index < columns match {
        case true => generateColumns(
          index + 1,
          seq :+ new Line {
            strokeWidth() = STROKE_WIDTH
            startY = 0
            endY <== Board.this.height
            startX <== (Board.this.width / columns) * index
            endX <== (Board.this.width / columns) * index
          }
        )
        case false => seq
      }
    }

    generateRows(1, Seq.empty) ++ generateColumns(1, Seq.empty)

  }

  children = generateLines


  def addPoint(row: Int, column: Int, colour: Color): Unit = {

    val circle = new Circle {
      centerY <== Board.this.height / rows * row
      centerX <== Board.this.width / columns * column
      radius = STROKE_WIDTH * 4
      fill = colour
    }

    children.add(circle.asInstanceOf[Node])
  }

}
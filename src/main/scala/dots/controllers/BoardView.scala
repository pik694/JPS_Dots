package dots.controllers

import javafx.beans.NamedArg
import scalafx.scene.Node
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Line}

import scala.annotation.tailrec


class BoardView(@NamedArg("rows") val rows: Int, @NamedArg("columns") columns: Int) extends Pane {

  private val STROKE_WIDTH: Double = 2

  children = generateLines

  private def generateLines: Seq[Line] = {

    @tailrec
    def generateRows(index: Int, seq: Seq[Line]): Seq[Line] = {
      index < rows match {
        case true => generateRows(
          index + 1,
          seq :+ new Line {
            strokeWidth() = STROKE_WIDTH
            startY <== (BoardView.this.height / rows) * index
            endY <== (BoardView.this.height / rows) * index
            startX = 0
            endX <== BoardView.this.width
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
            endY <== BoardView.this.height
            startX <== (BoardView.this.width / columns) * index
            endX <== (BoardView.this.width / columns) * index
          }
        )
        case false => seq
      }
    }

    generateRows(1, Seq.empty) ++ generateColumns(1, Seq.empty)

  }


  def addPoint(row: Int, column: Int, colour: Color): Unit = {

    val circle = new Circle {
      centerY <== BoardView.this.height / rows * row
      centerX <== BoardView.this.width / columns * column
      radius = STROKE_WIDTH * 4
      fill = colour
    }

    children.add(circle.asInstanceOf[Node])
  }

}
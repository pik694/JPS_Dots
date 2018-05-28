package dots.controls

import dots.model.{Dot, Point}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class DotView(val row: Int, val column: Int, val color: Color) extends Circle {
  def this(dot: Dot){
    this(dot.point.row, dot.point.column, PlayerView.color(dot.player))
  }

  radius = 6
  fill = color

}

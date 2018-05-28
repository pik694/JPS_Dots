package dots.model

import dots.model.player.Player
import scalafx.scene.paint.Color

case class Point(row: Int, column: Int)

case class Dot(point: Point, player: Player)

case class DotView(point: Point, color: Color)
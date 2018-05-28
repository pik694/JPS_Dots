package dots.model

import dots.model.player.Player

case class Point(row: Int, column: Int)

case class Dot(point: Point, player: Player)
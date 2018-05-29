package dots.model

import dots.model.player.Player

case class Point(row: Int, column: Int)


case class Dot(point: Point, player: Player)

private[model] case class MapDot(player: Player, value: Int = 0){
  def this(dot: Dot) = this (dot.player)
}
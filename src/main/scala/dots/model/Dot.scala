package dots.model

import dots.model.DotState.DotState
import dots.model.player.Player

case class Point(row: Int, column: Int)


case class Dot(point: Point, player: Player)


object DotState extends Enumeration {
  type DotState = Value

  val FREE, SELF_SURROUNDED, OPPONENT_SURROUNDED, FREE_SPACE: Value = Value

}

private[model] case class MapDot(player: Player, value: DotState = DotState.FREE){
  def this(dot: Dot) = this (dot.player)
}
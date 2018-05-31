package dots.model

import dots.model.DotState.DotState
import dots.model.player.Player

/**
  * Class representing 2D Point on board
  * @param row
  * @param column
  */
case class Point(row: Int, column: Int)

/**
  * Class representing place on board
  * @param point position on board
  * @param player reference to player standing here
  */
case class Dot(point: Point, player: Player)

/**
  * State of a dot
  * Necessary fo counting score
  */
object DotState extends Enumeration {
  type DotState = Value

  val FREE, SELF_SURROUNDED, OPPONENT_SURROUNDED, FREE_SPACE: Value = Value

}

private[model] case class MapDot(player: Player, value: DotState = DotState.FREE){
  def this(dot: Dot) = this (dot.player)
}
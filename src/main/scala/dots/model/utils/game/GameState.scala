package dots.model.utils.game

import dots.model.player.Player
import dots.model.utils.game.GameState.BoardType
import dots.model.{Dot, MapDot, Point}

import scala.collection.immutable.HashMap

object GameState{
  type BoardType = HashMap[Point, MapDot]
}

private[model] case class GameState(board: BoardType = new HashMap(), score: (Int, Int) = (0, 0), nextPlayer: Player){

  def +(dot: Dot): GameState ={
    this.copy(board = this.board + (dot.point -> MapDot(dot.player)))
  }
}


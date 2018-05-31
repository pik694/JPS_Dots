package dots.model.utils.game

import dots.model.player.Player
import dots.model.utils.game.GameState.BoardType
import dots.model.{Dot, MapDot, Point}

import scala.collection.immutable.HashMap

/**
  * Hashmap containing each move and it's state
  */
object GameState{
  type BoardType = HashMap[Point, MapDot]
}

/**
  * Class representing actual game state
  * @param board actual board
  * @param score actual score
  * @param nextPlayer player waiting for move
  */
private[model] case class GameState(board: BoardType = new HashMap(), score: (Int, Int) = (0, 0), nextPlayer: Player){

  /**
    * Add player move to game state
    * @param dot - player's move
    * @return new game state
    */
  def +(dot: Dot): GameState ={
    this.copy(board = this.board + (dot.point -> MapDot(dot.player)))
  }
}


package dots.model.player

import dots.model.utils.game.GameState
import dots.model.{Dot, Game, Point}

/**
  * Class representing human player
  */
class HumanPlayer extends Player {
  /**
    * Main human player's function
    * Adds dot to the game
    * @param point given by user
    */
  override def acceptMove(point: Point): Unit = {
    Game.move(Dot(point, this))
  }
}
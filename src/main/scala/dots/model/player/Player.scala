package dots.model.player

import dots.model.Point
import dots.model.utils.game.{Game, GameState}

/**
  * Interface of player classes
  */
trait Player {
  def setGame(game: Game){}
  def makeMove(gameState: GameState): Point = null
  def acceptMove(point: Point){}
}

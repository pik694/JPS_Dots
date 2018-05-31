package dots.model.player

import dots.model.Point
import dots.model.utils.game.{Game, GameState}

trait Player {
  def setGame(game: Game){}
  def makeMove(gameState: GameState): Point = null
  def acceptMove(point: Point){}
}

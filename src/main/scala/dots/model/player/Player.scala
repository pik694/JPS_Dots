package dots.model.player

import dots.model.Point
import dots.model.utils.game.GameState

trait Player {
  def makeMove(gameState: GameState): Point = null
  def acceptMove(point: Point){}
}

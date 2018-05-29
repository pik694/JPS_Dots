package dots.model.player

import dots.model.Point
import dots.model.utils.game.GameState

class ComputerPlayer extends Player {

  override def makeMove(gameState: GameState): Point = {
    val(move, _) = Negascout(0)
    move
  }

  def Negascout(depth: Int): (Point, Int) = {
    (Point(0,0), 0)
  }

}

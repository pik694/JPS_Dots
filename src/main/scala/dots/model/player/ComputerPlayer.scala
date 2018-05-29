package dots.model.player

import dots.model.{Game, Point}
import dots.model.utils.game.{Game, GameState}

class ComputerPlayer extends Player {

  override def makeMove(gameState: GameState): Point = {
    val(move, _) = Negascout(gameState, 0)
    move
  }

  private def Negascout(gameState: GameState, depth: Int): (Point, Int) = {
    val children = game.getEmptyPlaces(gameState)
    for( i <- children.indices )
      println(children(i).row, children(i).column)
    (Point(0,0), 0)
  }

  var game: Game = _
}

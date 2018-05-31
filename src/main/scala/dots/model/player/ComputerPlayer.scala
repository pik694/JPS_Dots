package dots.model.player

import dots.model.{Dot, Game, Point}
import dots.model.utils.game.{Game, GameState}

class ComputerPlayer extends Player {

  override def makeMove(gameState: GameState): Point = {
    val possibleMoves = game.getEmptyPlaces(gameState)
    val tmpMovesAndScores: Seq[(Point, Int)] = for( point <- possibleMoves )
                                                    yield ( point,
                                                            -Negascout(game.move(gameState, Dot(point, gameState.nextPlayer)), maxDepth, -1000, 1000, -1) )
    val sortedMoves = tmpMovesAndScores.sortBy(_._2).reverse
    sortedMoves.head._1
  }

  private def Negascout(gameState: GameState, depth: Int, alpha: Int, beta: Int, color: Int): Int = {
    val possibleMoves = game.getEmptyPlaces(gameState)
    if (depth == 0 || possibleMoves.isEmpty)
      color * game.getResultOfPlayer(gameState, this)
    else {
      var score: Int = 0
      var alpha_ = alpha

      def vfdgv = {
        for (i <- possibleMoves.indices if alpha_ < beta) {
          if (i == 0) {
            score = -Negascout(game.move(gameState, Dot(possibleMoves(i), gameState.nextPlayer)), depth - 1, -beta, -alpha_, -color)
          }
          else {
            score = -Negascout(game.move(gameState, Dot(possibleMoves(i), gameState.nextPlayer)), depth - 1, -alpha_ - 1, -alpha_, -color)
            if (alpha < score && score < beta)
              score = -Negascout(game.move(gameState, Dot(possibleMoves(i), gameState.nextPlayer)), depth - 1, -beta, -score, -color)
          }
          alpha_ = Math.max(alpha_, score)
        }
      }

      vfdgv
      alpha_
    }
  }

  var game: Game = _
  private val maxDepth = 1
}

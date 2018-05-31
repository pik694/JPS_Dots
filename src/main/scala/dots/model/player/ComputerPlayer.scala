package dots.model.player

import dots.model.utils.game.{Game, GameState}
import dots.model.{Dot, Point}

/**
  * A player class that represents computer
  */
class ComputerPlayer extends Player {

  /**
    * Function that return next move of computer player
    * @param gameState: actual state of the game
    * @return best predicted move
    */
  override def makeMove(gameState: GameState): Point = {
    val possibleMoves = game.getEmptyPlaces(gameState)
    val tmpMovesAndScores: Seq[(Point, Int)] = for (point <- possibleMoves)
      yield (point,
        -Negascout(game.move(gameState, Dot(point, gameState.nextPlayer), draw = false), maxDepth, Int.MinValue+1, Int.MaxValue, -1))
    val sortedMoves = tmpMovesAndScores.sortBy(_._2).reverse
    sortedMoves.head._1
  }

  /**
    * Function to predict best move for computer player.
    * Uses Negascout algorithm
    * Heuristic: to maximization of computer player score, offensive tactic
    * @param gameState next state of the game
    * @param depth actual depht of an algorithm
    * @param alpha param of Negascout algorithm
    * @param beta  param of Negascout algorithm
    * @param color actual player: 1 means player that negascout predicts move, -1 it's opponent
    * @return score of actual move
    */
  private def Negascout(gameState: GameState, depth: Int, alpha: Int, beta: Int, color: Int): Int = {
    val possibleMoves = game.getEmptyPlaces(gameState)
    if (depth == 0 || possibleMoves.isEmpty)
      color * game.getResultOfPlayer(gameState, this)
    else {
      var score: Int = 0

      var alpha_ = alpha

      for (i <- possibleMoves.indices if alpha_ < beta) {
        if (i == 0) {
          score = -Negascout(game.move(gameState, Dot(possibleMoves(i), gameState.nextPlayer), draw = false), depth - 1, -beta, -alpha_, -color)
        }
        else {
          score = -Negascout(game.move(gameState, Dot(possibleMoves(i), gameState.nextPlayer), draw = false), depth - 1, -alpha_ - 1, -alpha_, -color)
          if (alpha < score && score < beta)
            score = -Negascout(game.move(gameState, Dot(possibleMoves(i), gameState.nextPlayer), draw = false), depth - 1, -beta, -score, -color)
        }
        alpha_ = Math.max(alpha_, score)
      }

      alpha_
    }
  }

  /**
    * Set instance of game class
    * Instance of game class is used to control game rules, Negascout uses it to predict the best move
    * @param game
    */
  override def setGame(game: Game): Unit = {
    this.game = game
  }

  /**
    * Set max depth of Negascout algorithm
    * @param depth
    */
  def setNegascoutGepth(depth: Int): Unit = {
    maxDepth = depth
  }


  private var game: Game = _
  private var maxDepth: Int = _
}

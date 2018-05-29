package dots.model.utils.game

import dots.controllers.MainController
import dots.model.player.Player
import dots.model.utils.Hull
import dots.model.{Dot, MapDot, Point}

import scala.collection.immutable.HashMap

private[model] class Game(
                           private val playerA: Player,
                           private val playerB: Player,
                           private val rows: Int,
                           private val columns: Int,
                         ) {

  def isEndOfGame(state: GameState): Boolean = {
    state.board.size == rows * columns
  }

  def canMove(state: GameState, dot: Dot): Boolean = {
    (!state.board.contains(dot.point)
      && dot.player == state.nextPlayer
      && !isEndOfGame(state)
      && dot.point.row >= 0
      && dot.point.row < rows
      && dot.point.column >= 0
      && dot.point.column < columns
      )
  }


  def getEmptyPlaces(gameState: GameState): Seq[Point] = {

    val tmpPoints: Seq[Point] = for( i <- 0 to rows;
                         j <- 0 to columns )
                         yield Point(i,j)

    def emptyPlacesFilter(point: Point): Boolean = {
      !gameState.board.contains(point)
    }

    tmpPoints.filter(emptyPlacesFilter)
  }

  def move(gameState: GameState, dot: Dot): GameState = {
    if (canMove(gameState, dot)) {
      MainController.addDot(dot)

      val hull = tryFindHull(gameState.board, dot)

      if (hull != null){

      }

      return nextMove(gameState + dot)
    }

    gameState
  }

  private def nextMove(state: GameState): GameState = {
    val nextPlayer = if (state.nextPlayer == playerA) playerB else playerA
    state.copy(nextPlayer = nextPlayer)
  }

  private[game] def countPoints(board: HashMap[Point, MapDot], hull: Hull): (Int, Int) = {

    (1,0)
  }

  private[game] def tryFindHull(board: HashMap[Point, MapDot], dot: Dot): Hull = {

    def getChildren(parents: Seq[Point], point: Point): Seq[Point] = {

      def filter(point: Point): Boolean = {
        (board.contains(point)
          && board(point).player == dot.player
          && board(point).value >= 0
          && (!parents.contains(point) || (point == parents.last && parents.size > 2)) //to close a hull with a dot inside we need at least 4 points
          )
      }

      val tmp = for (deltaColumn <- Seq(-1, 0, 1) if point.column + deltaColumn >= 0 && point.column + deltaColumn < columns;
                     deltaRow <- Seq(-1, 0, 1) if point.row + deltaRow >= 0 && point.row + deltaRow < rows)
        yield Point(point.row + deltaRow, point.column + deltaColumn)

      for (child <- tmp if filter(child)) yield child

    }

    def foreachChild(parents: Seq[Point], children: Seq[Point]): Hull = {
      children match {
        case Nil => null
        case head :: tail => {
          val hull = findHull(head +: parents)
          if (hull == null)
            foreachChild(parents, tail)
          else
            hull
        }
      }
    }

    def findHull(parents: Seq[Point]): Hull = {

      val current = parents.head
      val children = getChildren(parents, current)

      if (children.isEmpty) return null

      if (children.contains(dot.point)) {
        val hull = Hull(parents)
        if (countPoints(board, hull) != (0, 0)) hull else null
      }
      else
        foreachChild(parents, children)

    }

    findHull(Seq(dot.point))

  }

  //  private def countPoints(hull: Seq[Dot]): Int = {
  //    1
  //  }

  //  private def applyHull(hull: Seq[Dot]) = {
  //    // TODO
  //  }

}
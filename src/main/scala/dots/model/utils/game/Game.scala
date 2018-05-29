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


  def move(gameState: GameState, dot: Dot): GameState = {
    if (canMove(gameState, dot)) {
      MainController.addDot(dot)
      return nextMove(gameState + dot)
    }

    gameState
  }

  private def nextMove(state: GameState): GameState = {
    val nextPlayer = if (state.nextPlayer == playerA) playerB else playerA
    state.copy(nextPlayer = nextPlayer)
  }

  private[game] def tryFindHull(board: HashMap[Point, MapDot], dot: Dot): Hull = {
    def getChildren(parents: Seq[Point], point: Point): Seq[Point] = {

      def filter(point: Point): Boolean = {
        (board(point).player == dot.player
          && (!parents.contains(point) || (point == parents.last || parents.size > 3)) //to close a hull with a dot inside we need at least 4 points
          )
      }

      val tmp = for (deltaColumn <- Seq(-1, 0, 1) if point.column + deltaColumn >= 0 && point.column + deltaColumn < columns;
                     deltaRow <- Seq(-1, 0, 1) if point.row + deltaRow >= 0 && point.row + deltaRow < rows)
        yield Point(point.row + deltaRow, point.column + deltaColumn)

      for (child <- tmp if filter(child)) yield child

    }

    null
  }


  //
  //  private def sortHull(hull: Seq[Dot]): Seq[Dot] = {
  //    hull
  //  }
  //  private def countPoints(hull: Seq[Dot]): Int = {
  //    val sortedHull = sortHull(hull)
  //    1
  //  }

  //  private def getChildren(dot: Dot, parents: Seq[Dot]): Seq[Dot] = {
  //
  //    //TODO: risky code by Bidzyyys xD
  //    def getNeighbour(dot: Dot, row_diff: Int, col_diff: Int): Dot = {
  //      if (dot.point.row + row_diff >= _matrix.length || dot.point.row + row_diff < 0 ||
  //      dot.point.column + col_diff >= matrix(0).length || dot.point.column + col_diff < 0)
  //      Dot(Point(-1, -1), null)
  //      else
  //      Dot(Point(dot.point.row + row_diff, dot.point.column + col_diff), _matrix(dot.point.row + row_diff)(dot.point.column + col_diff))
  //    }
  //
  //    def cus_filter(dot: Dot, player: Player): Boolean = {
  //      dot.player == player && (!parents.contains(dot) || (dot == parents.last && parents.size > 2))
  //    }
  //
  //    val tmpSeq = Seq[Dot](getNeighbour(dot, -1, -1), getNeighbour(dot, -1, 0), getNeighbour(dot, -1, 1),
  //    getNeighbour(dot, 1, 1), getNeighbour(dot, 1, 0), getNeighbour(dot, 1, -1),
  //    getNeighbour(dot, 0, 1), getNeighbour(dot, 0, 1))
  //
  //    tmpSeq.filter(cus_filter(_, dot.player))
  //  }
  //
  //  def findHull(start: Dot): (Seq[Dot], Int) = {
  //
  //    def foreachChild(hull: Seq[Dot], children: Seq[Dot]): (Seq[Dot], Int) = {
  //      children match {
  //        case Nil => (Seq.empty,0)
  //        case head :: tail => {
  //          val (tmp: Seq[Dot], result: Int) = findHull(head +: hull)
  //          if (tmp.isEmpty || result == 0) {
  //            foreachChild(hull, tail)
  //          }
  //          else
  //          (tmp, result)
  //        }
  //      }
  //    }
  //
  //    def findHull(hull: Seq[Dot]):  (Seq[Dot], Int) = {
  //
  //      val current :: tail = hull
  //      val children = getChildren(current, hull)
  //
  //      if (children.isEmpty) {
  //        (Seq.empty, 0)
  //      }
  //      else if (children.contains(start)) {
  //        val result = countPoints(hull)
  //        if (result > 0){
  //          (hull, result)
  //        }
  //        else (Seq.empty, 0)
  //      }
  //      else {
  //        foreachChild(hull, children)
  //      }
  //    }
  //
  //    findHull(Seq(start))
  //
  //  }
  //
  //
  //
  //  private def applyHull(hull: Seq[Dot]) = {
  //    // TODO
  //  }

}
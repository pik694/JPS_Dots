package dots.model.utils.game

import dots.controllers.MainController
import dots.model.player.Player
import dots.model.utils.Hull
import dots.model.{Dot, MapDot, Point}

import scala.annotation.tailrec
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

    val tmpPoints: Seq[Point] = for (i <- 0 to rows;
                                     j <- 0 to columns)
      yield Point(i, j)

    def emptyPlacesFilter(point: Point): Boolean = {
      !gameState.board.contains(point)
    }

    tmpPoints.filter(emptyPlacesFilter)
  }

  def move(gameState: GameState, dot: Dot): GameState = {
    if (canMove(gameState, dot)) {
      MainController.addDot(dot)
      var tmpState = gameState + dot

      val hull = tryFindHull(tmpState.board, dot)

      if (hull != null) {

        tmpState = applyHull(tmpState, hull)

      }

      return nextMove(tmpState)
    }

    gameState
  }

  private def isOutOfBounds(point: Point): Boolean = {
    (point.column < 0
      || point.column >= columns
      || point.row < 0
      || point.row >= rows)
  }

  private def nextMove(state: GameState): GameState = {
    val nextPlayer = if (state.nextPlayer == playerA) playerB else playerA
    state.copy(nextPlayer = nextPlayer)
  }

  private[game] def countPoints(board: HashMap[Point, MapDot], hull: Hull): (Int, Int) = {

    val player = board(hull.head).player
    val head = hull.head

    @tailrec
    def countPoints(buffer: Set[Point], searched: Set[Point] = Set.empty, result: (Int, Int) = (0, 0)): (Int, Int) = {

      def getChildren(point: Point): Set[Point] = {

        val verticalChildren = for (deltaRow <- Seq(-1, 1)) yield Point(point.row + deltaRow, point.column)
        val horizontalChildren = for (deltaColumn <- Seq(-1, 1)) yield Point(point.row, point.column + deltaColumn)

        val children = horizontalChildren ++ verticalChildren

        ((children.toSet -- searched) -- buffer) -- hull.dots

      }


      if (buffer.isEmpty) return result

      val current = buffer.head
      if (isOutOfBounds(current)) return (0, 0)

      val children: Set[Point] = getChildren(current)

      val delta: (Int, Int) = if (board.contains(current)) {
        val dot = board(current)
        if (dot.player != player && dot.value == 0)
          (1, -1)
        else
          (0, 0)
      }
      else
        (0, 0)


      val newResult: (Int, Int) = if (player == playerA)
        (result._1 + delta._1, result._2 + delta._2)
      else
        (result._1 + delta._2, result._2 + delta._1)

      countPoints(buffer.tail ++ children, searched + current, newResult)

    }

    val neighbours = Set(
      Point(head.row - 1, head.column - 1),
      Point(head.row - 1, head.column),
      Point(head.row - 1, head.column + 1),
      Point(head.row, head.column - 1),
      Point(head.row, head.column + 1),
      Point(head.row + 1, head.column - 1),
      Point(head.row + 1, head.column),
      Point(head.row + 1, head.column + 1)
    )

    @tailrec
    def search(neighbours: Set[Point]): (Int, Int) = {

      if (neighbours.isEmpty) return (0, 0)

      val currentResult = countPoints(Set(neighbours.head))

      if (currentResult != (0, 0)) currentResult
      else search(neighbours.tail)

    }

    search(neighbours -- hull.dots)

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

  private def applyHull(gameState: GameState, hull: Hull): GameState = {

    val dots = for (point <- hull.dots) yield Dot(point, gameState.nextPlayer)

    MainController.connectDots(dots)

    GameState(gameState.board, gameState.score, gameState.nextPlayer)
  }



}
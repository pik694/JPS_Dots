package dots.model.utils.game

import dots.controllers.MainController
import dots.model.player.Player
import dots.model.utils.Hull
import dots.model.utils.game.GameState.BoardType
import dots.model.{Dot, DotState, MapDot, Point}

import scala.annotation.tailrec

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
      var modifiedState = gameState + dot

      val result: (Hull, GameState) = tryFindHull(modifiedState, dot)

      if (result != null) {

        modifiedState = result._2
        applyHull(modifiedState, result._1)

      }

      return nextMove(modifiedState)
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

  private[game] def countPoints(board: BoardType): (Int, Int) = {

    def add(partialSum: (Int, Int), dot: (Point, MapDot)): (Int, Int) = {

      if (dot._2.value == DotState.SELF_SURROUNDED) {
        if (dot._2.player == playerA)
          return (partialSum._1 - 1, partialSum._2)
        else
          return (partialSum._1, partialSum._2 - 1)
      }
      else if (dot._2.value == DotState.OPPONENT_SURROUNDED) {
        if (dot._2.player == playerA)
          return (partialSum._1, partialSum._2 + 1)
        else
          return (partialSum._1 + 1, partialSum._2)
      }
      partialSum
    }

    board.foldLeft((0, 0))(add)

  }

  private[game] def tryApply(board: BoardType, hull: Hull): BoardType = {

    val player = board(hull.head).player
    val head = hull.head

    @tailrec
    def applyChanges(board: BoardType, buffer: Set[Point], searched: Set[Point] = Set.empty): BoardType = {

      def getChildren(point: Point): Set[Point] = {

        val verticalChildren = for (deltaRow <- Seq(-1, 1)) yield Point(point.row + deltaRow, point.column)
        val horizontalChildren = for (deltaColumn <- Seq(-1, 1)) yield Point(point.row, point.column + deltaColumn)

        val children = horizontalChildren ++ verticalChildren

        ((children.toSet -- searched) -- buffer) -- hull.dots

      }


      if (buffer.isEmpty) return board

      val current = buffer.head
      if (isOutOfBounds(current)) return null

      val children: Set[Point] = getChildren(current)

      val modifiedBoard: BoardType = if (board.contains(current)){
        val dot = board(current)
        if (dot.value == DotState.FREE){
          if (dot.player == player) board + (current -> dot.copy(value = DotState.SELF_SURROUNDED))
          else board + (current -> dot.copy(value = DotState.OPPONENT_SURROUNDED))
        }
        else board
      }
      else board + (current -> MapDot(null, DotState.FREE_SPACE))


      applyChanges(modifiedBoard, buffer.tail ++ children, searched + current)

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
    def search(neighbours: Set[Point]): BoardType = {

      if (neighbours.isEmpty) return null

      val currentResult = applyChanges(board, Set(neighbours.head))

      if (currentResult != null) currentResult

      else search(neighbours.tail)

    }

    search(neighbours -- hull.dots)

  }

  private[game] def tryFindHull(state: GameState, dot: Dot): (Hull, GameState) = {

    val board = state.board

    def getChildren(parents: Seq[Point], point: Point): Seq[Point] = {

      def filter(point: Point): Boolean = {
        (board.contains(point)
          && board(point).player == dot.player
          && board(point).value == DotState.FREE
          && (!parents.contains(point) || (point == parents.last && parents.size > 2)) //to close a hull with a dot inside we need at least 4 points
          )
      }

      val tmp = for (deltaColumn <- Seq(-1, 0, 1) if point.column + deltaColumn >= 0 && point.column + deltaColumn < columns;
                     deltaRow <- Seq(-1, 0, 1) if point.row + deltaRow >= 0 && point.row + deltaRow < rows)
        yield Point(point.row + deltaRow, point.column + deltaColumn)

      for (child <- tmp if filter(child)) yield child

    }

    @tailrec
    def foreachChild(parents: Seq[Point], children: Seq[Point]): (Hull, GameState) = {
      children match {
        case Nil => null
        case head :: tail => {
          val update = findHull(head +: parents)
          if (update == null)
            foreachChild(parents, tail)
          else
            update
        }
      }
    }


    def findHull(parents: Seq[Point]): (Hull, GameState) = {

      val current = parents.head
      val children = getChildren(parents, current)

      if (children.isEmpty)
        return null

      if (children.contains(dot.point)) {

        val hull = Hull(parents)
        val newBoard = tryApply(board, hull)

        if (newBoard == null) return null

        val newScore = countPoints(newBoard)

        if (newScore._1 > state.score._1 && state.nextPlayer == playerA || newScore._2 > state.score._2 && state.nextPlayer == playerB)
          (hull, state.copy(board = newBoard, score = newScore))
        else null

      }
      else
        foreachChild(parents, children)

    }

    findHull(Seq(dot.point))

  }

  private def applyHull(gameState: GameState, hull: Hull): Unit= {

    val dots = for (point <- hull.dots) yield Dot(point, gameState.nextPlayer)
    MainController.connectDots(dots)

  }


}
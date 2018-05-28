package dots.model

import dots.controllers.MainController
import dots.model.player.Player
import scalafx.beans.property.ObjectProperty

class Game(val playerA: Player, val playerB: Player, private val _matrix: Array[Array[Player]]) {

  def this(rows: Int, columns: Int, playerA: Player, playerB: Player) = this(playerA, playerB, Array.ofDim[Player](rows, columns))

  private val _score = new ObjectProperty[(Int, Int)]()
  private val _move = new ObjectProperty[Player]()

  _move() = playerA

  def score = _score

  def nextMovePlayer = _move

  def matrix = _matrix

  def isEndOfGame: Boolean = false

  def getChildren(dot: Dot, parents: Seq[Dot]): Seq[Dot] = {

    //TODO: risky code by Bidzyyys xD
    def getNeighbour(dot: Dot, row_diff: Int, col_diff: Int): Dot = {
      if (dot.point.row + row_diff >= _matrix.length || dot.point.row + row_diff < 0 ||
        dot.point.column + col_diff >= matrix(0).length || dot.point.column + col_diff < 0)
        Dot(Point(-1, -1), null)
      else
        Dot(Point(dot.point.row + row_diff, dot.point.column + col_diff), _matrix(dot.point.row + row_diff)(dot.point.column + col_diff))
    }

    def cus_filter(dot: Dot, player: Player): Boolean = {
      dot.player == player && (!parents.contains(dot) || (dot == parents.last && parents.size > 2))
    }

    val tmpSeq = Seq[Dot](getNeighbour(dot, -1, -1), getNeighbour(dot, -1, 0), getNeighbour(dot, -1, 1),
      getNeighbour(dot, 1, 1), getNeighbour(dot, 1, 0), getNeighbour(dot, 1, -1),
      getNeighbour(dot, 0, 1), getNeighbour(dot, 0, 1))

    tmpSeq.filter(cus_filter(_, dot.player))
  }


  //  def isClosedArea(dot: Dot): Boolean = {
  //
  //    def dfsIsClosedArea(area: Seq[Dot], toVisit: Seq[Dot]): Boolean = {
  //
  //      if(toVisit.isEmpty)
  //        false
  //      else if (toVisit.head == dot && area.last != dot)
  //        true
  //      else
  //          dfsIsClosedArea(area ++ Seq[Dot](toVisit.head), getNeighbours(toVisit.head) ++ toVisit.tail)
  //    }
  //    dfsIsClosedArea(Seq[Dot](dot), getNeighbours(dot))
  //  }

  def canMove(dot: Dot): Boolean = {
    _matrix(dot.point.row)(dot.point.column) == null
  }

  def findHull(start: Dot): Seq[Dot] = {

    def foreachChild(hull: Seq[Dot], children: Seq[Dot]): Seq[Dot] = {
      children match {
        case Nil => Seq.empty
        case head :: tail => {
          val tmp = findHull(head +: hull)
          if (tmp.isEmpty) {
            foreachChild(hull, tail)
          }
          else
            tmp
        }
      }
    }

    def findHull(hull: Seq[Dot]): Seq[Dot] = {

      val current :: tail = hull
      val children = getChildren(current, hull)

      if (children.isEmpty) {
        Seq.empty
      }
      else if (children.contains(start)) {
        hull
      }
      else {
        foreachChild(hull, children)
      }
    }

    findHull(Seq(start))

  }

  def move(dot: Dot): Unit = {
    if (canMove(dot)) {
      MainController.addDot(dot)
      matrix(dot.point.row)(dot.point.column) = dot.player

      val hull: Seq[Dot] = findHull(dot)
      if (hull.nonEmpty){
          score() = (1,0)
      }
    }
  }


}

object Game {

  var delegate: Game = null

  private val _playerA = new ObjectProperty[Player]()
  private val _playerB = new ObjectProperty[Player]()

  private val _score = new ObjectProperty[(Int, Int)]()

  private val _move = new ObjectProperty[Player]()

  def playerA = _playerA

  def playerB = _playerB

  def score = _score

  def nextMovePlayer = _move


  def apply(rows: Int, columns: Int, playerA: Player, playerB: Player) {
    delegate = new Game(rows, columns, playerA, playerB)
    _playerA() = playerA
    _playerB() = playerB

    _score <== delegate.score
    _move <== delegate.nextMovePlayer

  }

  def isEndOfGame: Boolean = delegate.isEndOfGame

  def canMove(dot: Dot): Boolean = delegate.canMove(dot)

  def move(dot: Dot): Unit = delegate.move(dot)
}


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

  def canMove(dot: Dot): Boolean = {
    _matrix(dot.point.row)(dot.point.column) == null
  }

  def move(dot: Dot): Unit = {
    if (canMove(dot)) {
      _matrix(dot.point.row)(dot.point.column) = dot.player

//      score() = (score()._1 + 1 ,score()._2+1)

      MainController.addDot(dot)
      if (nextMovePlayer() == playerA){
        nextMovePlayer() = playerB
      }
      else{
        nextMovePlayer() = playerA
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


package dots.model

import dots.model.player.Player
import scalafx.beans.property.ObjectProperty

class Game {

  private val _playerA = new ObjectProperty[Player]()
  private val _playerB = new ObjectProperty[Player]()

  private var _matrix: Array[Array[Player]] = _

  private val _score = new ObjectProperty[(Int, Int)]()

  private val _move = new ObjectProperty[Player]()


  def playerA = _playerA
  def playerB = _playerB

  def score = _score
  def nextMovePlayer = _move

  def matrix = _matrix


  def startGame(rows: Int, columns: Int, playerA:Player, playerB: Player)={
    _matrix = Array.ofDim[ Player ](rows, columns)
    _playerA() = playerA
    _playerB() = playerB
  }

  def isEndOfGame(): Boolean = false

  def canMove(dot: Dot): Boolean = {
    _matrix(dot.point.row)(dot.point.column) == null
  }

  def move(dot: Dot): Unit = {
    if(canMove(dot))
      _matrix(dot.point.row)(dot.point.column) = dot.player
  }

}


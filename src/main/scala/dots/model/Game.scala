package dots.model

import dots.model.Game._move
import dots.model.player.Player
import scalafx.beans.property.ObjectProperty

class Game(val playerA: Player,val playerB: Player, private val _matrix: Array[Array[Player]]) {

  private val _score = new ObjectProperty[(Int, Int)]()
  private val _move = new ObjectProperty[Player]()

  def this(rows: Int, columns: Int, playerA:Player, playerB: Player) = this(playerA, playerB, Array.ofDim[Player](rows, columns))

  def score = _score

  def nextMovePlayer = _move

  def matrix = _matrix

  def isEndOfGame: Boolean = false

  def canMove(dot: Dot): Boolean = {
    _matrix(dot.point.row)(dot.point.column) == null
  }

  def move(dot: Dot): Unit = {
    if(canMove(dot))
      _matrix(dot.point.row)(dot.point.column) = dot.player
  }

}

object Game {

  var game: Game = null

  private val _playerA = new ObjectProperty[Player]()
  private val _playerB = new ObjectProperty[Player]()

  private val _score = new ObjectProperty[(Int, Int)]()

  private val _move = new ObjectProperty[Player]()

  def playerA = _playerA
  def playerB = _playerB

  def score = _score
  def nextMovePlayer = _move


  def apply(rows: Int, columns: Int, playerA: Player, playerB: Player){
    game = new Game(rows, columns, playerA, playerB)

    _playerA() = playerA
    _playerB() = playerB

    _score <== game.score
    _move  <== game.nextMovePlayer

  }

  def isEndOfGame: Boolean = game.isEndOfGame

  def canMove(dot: Dot): Boolean = game.canMove(dot)
  def move(dot: Dot): Unit = game.move(dot)
}


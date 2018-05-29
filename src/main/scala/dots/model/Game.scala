package dots.model

import dots.model.player.Player
import dots.model.utils.game.{Game, GameState}
import scalafx.beans.property.ObjectProperty

object Game {

  var delegate: Game = null
  private var _gameState: GameState = null

  val initialGameState: GameState = GameState(nextPlayer = _playerA())

  private val _playerA = new ObjectProperty[Player]()
  private val _playerB = new ObjectProperty[Player]()

  private val _score = new ObjectProperty[(Int, Int)]()

  private val _move = new ObjectProperty[Player]()

  def playerA: ObjectProperty[Player] = _playerA

  def playerB: ObjectProperty[Player] = _playerB

  def score: ObjectProperty[(Int, Int)] = _score

  def nextMovePlayer: ObjectProperty[Player] = _move


  def apply(rows: Int, columns: Int, playerA: Player, playerB: Player) {

    _playerA() = playerA
    _playerB() = playerB

    delegate = new Game(playerA, playerB, rows, columns)
    _gameState = initialGameState

  }

  def isEndOfGame: Boolean = delegate.isEndOfGame(_gameState)

  def canMove(dot: Dot): Boolean = delegate.canMove(_gameState, dot)

  def move(dot: Dot): Unit = {
    _gameState = delegate.move(_gameState, dot)
    _score() = _gameState.score
    _move() = _gameState.nextPlayer

  }
}


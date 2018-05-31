package dots.model

import dots.model.player.Player
import dots.model.utils.game.{Game, GameState}
import scalafx.beans.property.ObjectProperty

import scala.annotation.tailrec

/**
  * Singleton wrapper of game class
  * Responsible for controlling game rules
  */
object Game {

  /** holds instance of game class and actual game state */
  var delegate: Game = _
  private var _gameState: GameState = _

  /**
    * Function to initialize game
    * @return initial state of the game
    */
  def initialGameState: GameState = GameState(nextPlayer = _playerA())

  private val _playerA = new ObjectProperty[Player]()
  private val _playerB = new ObjectProperty[Player]()

  private val _score = new ObjectProperty[(Int, Int)]()

  private val _move = new ObjectProperty[Player]()

  /**
    * Getter
    * @return first player
    */
  def playerA: ObjectProperty[Player] = _playerA

  /**
    * Getter
    * @return second player
    */
  def playerB: ObjectProperty[Player] = _playerB

  /**
    * Getter
    * @return actual score of first and second player
    */
  def score: ObjectProperty[(Int, Int)] = _score


  /**
    * Getter
    * @return player that is waiting for move
    */
  def nextMovePlayer: ObjectProperty[Player] = _move


  /**
    * Initialize game
    * @param rows number of board rows
    * @param columns number of board columns
    * @param playerA first player
    * @param playerB second player
    */
  def apply(rows: Int, columns: Int, playerA: Player, playerB: Player) {

    _playerA() = playerA
    _playerB() = playerB

    delegate = new Game(playerA, playerB, rows, columns)
    playerA.setGame(delegate)
    playerB.setGame(delegate)

    _gameState = initialGameState

    _move() = initialGameState.nextPlayer

    val computedMove = initialGameState.nextPlayer.makeMove(initialGameState)
    if (computedMove != null) move(Dot(computedMove, _gameState.nextPlayer))
  }

  /**
    * Check if this is the end of the game
    * @return true if there is no empty places
    */
  def isEndOfGame: Boolean = delegate.isEndOfGame(_gameState)

  /**
    *
    * @param dot place where player wanna move
    * @return true if that place is empty
    */
  def canMove(dot: Dot): Boolean = delegate.canMove(_gameState, dot)

  /**
    * Function to return places where players can move
    * @param gameState actual state of the game
    * @return sequency of empty places
    */
  def getEmptyPlaces(gameState: GameState): Seq[Point] = {
    delegate.getEmptyPlaces(gameState)
  }

  /**
    * Function that controls game
    * @param dot place where actual player is going to move
    */
  @tailrec
  def move(dot: Dot): Unit = {

    _gameState = delegate.move(_gameState, dot)
    _score() = _gameState.score
    _move() = _gameState.nextPlayer

    if (!delegate.isEndOfGame(_gameState)) {
      val computedMove = _gameState.nextPlayer.makeMove(_gameState)
      if (computedMove != null) move(Dot(computedMove, _gameState.nextPlayer))
    }
  }
}


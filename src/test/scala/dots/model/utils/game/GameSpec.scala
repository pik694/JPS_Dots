package dots.model.utils.game

import dots.model.player.{MockPlayer, Player}
import dots.model.{Dot, DotState, MapDot, Point}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

import scala.annotation.tailrec
import scala.collection.immutable.HashMap

class GameSpec extends FlatSpec with Matchers with GivenWhenThen {

  final def getGameAndInitialState(playerA: Player = new MockPlayer, playerB: Player = new MockPlayer()): (Game, GameState) = {
    val game = new Game(playerA, playerB, 10, 10)
    (game, GameState(nextPlayer = playerA))
  }

  final def makeMoves(playerA: MockPlayer, playerB: MockPlayer, game: Game, gameState: GameState) : GameState = {

    @tailrec
    def makeRound(gameState: GameState): GameState ={
      val playerAMove = playerA.makeMove
      val playerBMove = playerB.makeMove
      if (playerAMove == null) gameState
      else {
        val tmpState = game.move(gameState, Dot(playerAMove, playerA))
        if (playerBMove == null) tmpState
        else makeRound(game.move(tmpState, Dot(playerBMove, playerB)))
      }
    }

    makeRound(gameState)

  }

  "Newly created game" should "have be initialised" in {

    Given("New game")

    val (game, state) = getGameAndInitialState()

    Then("board should be empty")

    state.board.isEmpty should be(true)

    Then("score should be 0-0")

    state.score should equal((0, 0))

    Then("it should not be an end")

    game.isEndOfGame(state) should be(false)

  }

  it should "accept first valid move" in {

    Given("an empty game")
    val playerA: MockPlayer = new MockPlayer(List(Point(0, 0)))
    val (game, initState) = getGameAndInitialState(playerA = playerA)

    When("playerA wants to make first move")

    val finalState = game.move(initState, Dot(playerA.makeMove, playerA))

    Then("board is not empty")

    finalState.board.isEmpty should be(false)

    Then("result equals to 0")

    finalState.score should equal((0, 0))

    Then("next player changes")

    finalState.nextPlayer should not equal playerA

  }

  it should "not enable user whose not turn is to move" in {

    Given("an empty game")
    val playerB: MockPlayer = new MockPlayer(List(Point(0, 0)))
    val (game, initState) = getGameAndInitialState(playerB = playerB)

    When("playerB wants to make first move")

    val finalState = game.move(initState, Dot(playerB.makeMove, playerB))

    Then("state should remain unchanged")

    finalState should equal(initState)

  }

  it should "not enable move that is out of bounds" in {

    Given("an empty game")
    val playerA: MockPlayer = new MockPlayer(List(Point(-1, 0), Point(10, 0), Point(0, -1), Point(0, 10)))
    val (game, initState) = getGameAndInitialState(playerA = playerA)

    When("playerA wants to make invalid move")

    var state = game.move(initState, Dot(playerA.makeMove, playerA))

    Then("state should remain unchanged")

    state should equal(initState)

    When("playerA wants to make invalid move")

    state = game.move(initState, Dot(playerA.makeMove, playerA))

    Then("state should remain unchanged")

    state should equal(initState)

    When("playerA wants to make invalid move")

    state = game.move(initState, Dot(playerA.makeMove, playerA))

    Then("state should remain unchanged")

    state should equal(initState)
    When("playerA wants to make invalid move")

    state = game.move(initState, Dot(playerA.makeMove, playerA))

    Then("state should remain unchanged")

    state should equal(initState)

  }


  it should "accept two consecutive, valid moves" in {

    Given("an empty game")
    val playerA: MockPlayer = new MockPlayer(List(Point(0, 0)))
    val playerB: MockPlayer = new MockPlayer(List(Point(1, 0)))
    val (game, initState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    When("playerA wants to make first move")

    val tmpState = game.move(initState, Dot(playerA.makeMove, playerA))
    val finalState = game.move(tmpState, Dot(playerB.makeMove, playerB))

    Then("board has two dots")

    finalState.board.size should equal(2)

    Then("result equals to 0")

    finalState.score should equal((0, 0))

    Then("next player changes")

    finalState.nextPlayer should equal(playerA)

  }

  it should "not accept two consecutive moves of the same player" in {

    Given("an empty game")
    val playerA: MockPlayer = new MockPlayer(List(Point(0, 0), Point(1, 0)))
    val (game, initState) = getGameAndInitialState(playerA = playerA)

    When("playerA wants to make first move")

    val tmpState = game.move(initState, Dot(playerA.makeMove, playerA))
    val finalState = game.move(tmpState, Dot(playerA.makeMove, playerA))

    Then("board has one dot")

    finalState.board.size should equal(1)

    Then("result equals to 0")

    finalState.score should equal((0, 0))

    Then("next player changes only once")

    finalState.nextPlayer should not equal (playerA)

  }

  "Algorithm to find hull" should "find simple hull" in {

    Given("New game and prepared board")

    val playerA: MockPlayer = new MockPlayer
    val playerB: MockPlayer = new MockPlayer

    val (game, initialState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    val initBoard = HashMap[Point, MapDot](
      Point(0, 1) -> MapDot(playerA),
      Point(1, 2) -> MapDot(playerA),
      Point(2, 1) -> MapDot(playerA),

      Point(1, 1) -> MapDot(playerB)
    )

    When("I try to find a hull created by a missing point")

    val missingPoint = Point(1, 0)

    val modifiedBoard = initBoard + (missingPoint -> MapDot(playerA))

    val tmpState = initialState.copy(board = modifiedBoard)

    val (hull, finalState) = game.tryFindHull(tmpState, Dot(missingPoint, playerA))

    Then("I should get a hull")

    hull should not be null

    Then("Final result should be 1-0")

    finalState.score should equal ((1,0))

  }

  it should "not find a hull when there is none" in {

    Given("New game and prepared board")

    val playerA: MockPlayer = new MockPlayer
    val playerB: MockPlayer = new MockPlayer

    val (game, initialState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    val initBoard = HashMap[Point, MapDot](
      Point(0, 1) -> MapDot(playerA),
      Point(1, 2) -> MapDot(playerA),
      Point(2, 1) -> MapDot(playerA),

      Point(1, 1) -> MapDot(playerB)
    )

    When("I try to find a hull created by a point")

    val point = Point(2, 0)

    val finalBoard = initBoard + (point -> MapDot(playerA))
    val finalState = initialState.copy(board = finalBoard)

    val result = game.tryFindHull(finalState, Dot(point, playerA))

    Then("I should not get an update")

    result should be(null)

  }

  it should "not find a hull when one of the points is claimed by the opponent" in {

    Given("New game and prepared board")

    val playerA: MockPlayer = new MockPlayer
    val playerB: MockPlayer = new MockPlayer

    val (game, initialState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    val initBoard = HashMap[Point, MapDot](
      Point(0, 1) -> MapDot(playerA),
      Point(1, 2) -> MapDot(playerA, DotState.OPPONENT_SURROUNDED),
      Point(2, 1) -> MapDot(playerA),

      Point(1, 1) -> MapDot(playerB)
    )

    When("I try to find a hull created by a point")

    val point = Point(1, 0)

    val finalBoard = initBoard + (point -> MapDot(playerA))
    val finalState = initialState.copy(board = finalBoard)

    val result = game.tryFindHull(finalState, Dot(point, playerA))

    Then("I should not get an update")

    result should be(null)

  }

  "Points counter" should "count one dot when created simple hull" in {
    Given("new game, prepared board and a hull")

    val playerA: MockPlayer = new MockPlayer
    val playerB: MockPlayer = new MockPlayer

    val (game, initState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    val initBoard = HashMap[Point, MapDot](

      Point(0, 1) -> MapDot(playerA),
      Point(1, 0) -> MapDot(playerA),
      Point(1, 2) -> MapDot(playerA),
      Point(2, 1) -> MapDot(playerA),

      Point(1, 1) -> MapDot(playerB)
    )

    val tmpState = initState.copy(board = initBoard)

    val (_, finalState) = game.tryFindHull(tmpState, Dot(Point(0, 1), playerA))

    When("I try to count score")

    val score = game.countPoints(finalState.board)

    Then("score should be 1-0")

    score should equal((1, 0))

  }


  "An attempt to create first hull" should "create first hull and update score" in {

    Given("a game with some dots that do not create a single hull")

    val playerA: MockPlayer = new MockPlayer
    val playerB: MockPlayer = new MockPlayer

    val (game, tmpState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    val initBoard = HashMap[Point, MapDot](
      Point(0, 1) -> MapDot(playerA),
      Point(1, 2) -> MapDot(playerA),
      Point(2, 1) -> MapDot(playerA),

      Point(1, 1) -> MapDot(playerB)
    )

    val initState = tmpState.copy(board = initBoard)


    When("I add one missing dot to create a hull")

    val finalState = game.move(initState, Dot(Point(1, 0), playerA))

    Then("the dot was added")
    finalState.board.size should equal(5)

    Then("score changed")

    finalState.score should equal((1, 0))

  }

  "Some game" should "accept a sequence of moves" in {

    Given("new game, players and their moves")

    val playerAMoves = List(
      Point(0,0)
    )

    val playerBMoves = List(
      Point(1,0)
    )

    val playerA: MockPlayer = new MockPlayer(playerAMoves)
    val playerB: MockPlayer = new MockPlayer(playerBMoves)

    val (game, initState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    When(" players make their moves")

    val finalState = makeMoves(playerA, playerB, game, initState)

    Then("board should contain all moves")
    finalState.board.size should equal(playerAMoves.size + playerBMoves.size)

    Then("score did not change")

    finalState.score should equal((0, 0))

  }

  it should "accept a sequence of moves and find a simple hull" in {

    Given("new game, players and their moves")

    val playerAMoves = List(
      Point(0,1),
      Point(1,2),
      Point(2,1),
      Point(1,0)
    )

    val playerBMoves = List(
      Point(1,1),
      Point(0,9),
      Point(0,8)
    )

    val playerA: MockPlayer = new MockPlayer(playerAMoves)
    val playerB: MockPlayer = new MockPlayer(playerBMoves)

    val (game, initState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    When(" players make their moves")

    val finalState = makeMoves(playerA, playerB, game, initState)

    Then("board should contain all moves")
    finalState.board.size should equal(playerAMoves.size + playerBMoves.size)

    Then("score did not change")

    finalState.score should equal((1, 0))

  }

  it should "accept a sequence of moves and find a hull with two dots inside" in {

    Given("new game, players and their moves")

    //TODO: add missing points to this case
    val playerAMoves = List(
      Point(0,0),
      Point(1,0),
      Point(2,0),
      Point(0,1),
      Point(0,2),
      Point(1,2),
      Point(2,2),
      Point(3,1)
    )

    val playerBMoves = List(
      Point(1,1),
      Point(2,1),
      Point(0,9),
      Point(0,8),
      Point(0,7),
      Point(0,6),
      Point(0,5)
    )

    val playerA: MockPlayer = new MockPlayer(playerAMoves)
    val playerB: MockPlayer = new MockPlayer(playerBMoves)

    val (game, initState) = getGameAndInitialState(playerA = playerA, playerB = playerB)

    When(" players make their moves")

    val finalState = makeMoves(playerA, playerB, game, initState)

    Then("board should contain all moves")
    finalState.board.size should equal(playerAMoves.size + playerBMoves.size)

    Then("score did not change")

    finalState.score should equal((2, 0))

  }

}
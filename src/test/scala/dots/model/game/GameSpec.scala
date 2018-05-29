package dots.model.game

import dots.model.{Dot, Point}
import dots.model.player.{MockPlayer, Player}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class GameSpec extends FlatSpec with Matchers with GivenWhenThen {

  final def getGameAndInitialState(playerA: Player = new MockPlayer, playerB: Player = new MockPlayer()): (Game, GameState) = {
    val game = new Game(playerA, playerB, 10, 10);
    (game, GameState(nextPlayer = playerA))
  }

  //    @tailrec
  //  final def addPoints(game: Game, dots: Seq[Dot]): Game ={
  //    dots match {
  //      case Nil => game
  //      case head :: tail =>{
  //        game.matrix(head.point.row)(head.point.column) = head.player
  //        addPoints(game, tail)
  //      }
  //    }
  //  }
  //

  "Newly created game" should "have be initialised" in {

    Given("New game")

    val (game, state) = getGameAndInitialState()

    Then("board should be empty")

    state.board.isEmpty should be (true)

    Then("score should be 0-0")

    state.score should equal ((0,0))

    Then("it should not be an end")

    game.isEndOfGame(state) should be (false)

  }

    it should "accept first valid move" in {

      Given("an empty game")
      val playerA: Player = new MockPlayer(List(Point(0,0)))
      val (game, initState) = getGameAndInitialState(playerA = playerA)

      When("playerA wants to make first move")

      val finalState = game.move(initState, Dot(playerA.makeMove(), playerA))

      Then("board is not empty")

      finalState.board.isEmpty should be (false)

      Then("result equals to 0")

      finalState.score should equal((0,0))

      Then("next player changes")

      finalState.nextPlayer should not equal playerA

    }

    it should "not enable user whose not turn is to move" in {

      Given("an empty game")
      val playerB: Player = new MockPlayer(List(Point(0,0)))
      val (game, initState) = getGameAndInitialState(playerB = playerB)

      When("playerB wants to make first move")

      val finalState = game.move(initState, Dot(playerB.makeMove(), playerB))

      Then("state should remain unchanged")

      finalState should equal (initState)

    }

  it should "not enable move that is out of bounds" in {

    Given("an empty game")
    val playerA: Player = new MockPlayer(List(Point(-1,0), Point(10, 0), Point(0,-1), Point(0,10)))
    val (game, initState) = getGameAndInitialState(playerA = playerA)

    When("playerA wants to make invalid move")

    var state = game.move(initState, Dot(playerA.makeMove(), playerA))

    Then("state should remain unchanged")

    state should equal (initState)

    When("playerA wants to make invalid move")

    state = game.move(initState, Dot(playerA.makeMove(), playerA))

    Then("state should remain unchanged")

    state should equal (initState)

    When("playerA wants to make invalid move")

    state = game.move(initState, Dot(playerA.makeMove(), playerA))

    Then("state should remain unchanged")

    state should equal (initState)
    When("playerA wants to make invalid move")

    state = game.move(initState, Dot(playerA.makeMove(), playerA))

    Then("state should remain unchanged")

    state should equal (initState)

  }

  //  "Simple area" should "close border with one dot inside" in {
  //    Given("Simple area with 3 blue and 1 red dot")
  //
  //    val playerA = new HumanPlayer
  //    val playerB = new HumanPlayer
  //
  //    val dots = Seq(
  //
  //      Dot(Point(1,1), playerB),
  //
  //      Dot(Point(1,2), playerA),
  //      Dot(Point(2,1), playerA),
  //      Dot(Point(1,0), playerA)
  //    )
  //
  //    val game = addPoints(new Game(10, 10, playerA, playerB), dots)
  //
  //
  //    When("I add one missing dot")
  //
  //    game.move(Dot(Point(0,1), playerA))
  //
  //    Then("Result should be 1-0")
  //
  //    assert(game.score() == (1,0))
  //
  //  }
  //
  //  it should "close border with one dot inside and not include all dots in the hull" in {
  //    Given("Simple area with 4 blue and 1 red dot")
  //
  //    val playerA = new HumanPlayer
  //    val playerB = new HumanPlayer
  //
  //    val dots = Seq(
  //
  //      Dot(Point(1,1), playerB),
  //
  //      Dot(Point(1,2), playerA),
  //      Dot(Point(2,1), playerA),
  //      Dot(Point(3,1), playerA),
  //      Dot(Point(1,0), playerA)
  //    )
  //
  //    val game = addPoints(new Game(10, 10, playerA, playerB), dots)
  //
  //
  //    When("I add one missing dot")
  //
  //    game.move(Dot(Point(0,1), playerA))
  //
  //    Then("Result should be 1-0")
  //
  //    assert(game.score() == (1,0))
  //
  //  }
  //
  //
  //  it should "not close hull as other players dot is already " in {
  //    Given("Simple area with 4 blue and 1 red dot")
  //
  //    val playerA = new HumanPlayer
  //    val playerB = new HumanPlayer
  //
  //    val dots = Seq(
  //
  //      Dot(Point(1,1), playerB, -1),
  //
  //      Dot(Point(1,2), playerA),
  //      Dot(Point(2,1), playerA),
  //      Dot(Point(3,1), playerA),
  //      Dot(Point(1,0), playerA)
  //    )
  //
  //    val game = addPoints(new Game(10, 10, playerA, playerB), dots)
  //
  //
  //    When("I add one missing dot")
  //
  //    game.move(Dot(Point(0,1), playerA))
  //
  //    Then("Result should be 1-0")
  //
  //    assert(game.score() == (0,0))
  //
  //  }

  //  "Given dot" should "should not recognise base" in {
  //
  //    Given("Empty game")
  //    val playerA = new HumanPlayer
  //    val playerB = new ComputerPlayer
  //    val game = new Game(2, 2, playerA, playerB)
  //
  //    game.move(Dot(Point(0, 0), playerA))
  //
  //    When("Player add one dot")
  //    val dot = new Dot(Point(0, 0), playerB)
  //    game.move(dot)
  //
  //    Then("Closed area should not be found")
  //    assert(!game.isClosedArea(dot))
  //  }
  //
  //  "Given closed area" should "should find closed area" in {
  //
  //    Given("Empty game")
  //    val playerA = new HumanPlayer
  //    val playerB = new ComputerPlayer
  //    val game = new Game(2, 2, playerA, playerB)
  //
  //    When("Player add 4 dots")
  //    game.move(Dot(Point(0, 0), playerA))
  //    game.move(Dot(Point(1, 0), playerA))
  //    game.move(Dot(Point(0, 1), playerA))
  //    game.move(Dot(Point(1, 1), playerA))
  //
  //
  //    Then("Closed area should be recognized")
  //    assert(game.isClosedArea(Dot(Point(1, 1), playerA)))
  //  }

}

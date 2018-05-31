package dots.model.player

import dots.model.{Dot, Point}
import dots.model.utils.game.{Game, GameState}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}


class ComputerPlayerSpec extends FlatSpec with Matchers with GivenWhenThen{

  final def getGameAndInitialState(playerA: Player = new MockPlayer(), playerB: Player = new MockPlayer(), rows: Int = 3, columns: Int = 3): (Game, GameState) = {
    val game = new Game(playerA, playerB, rows, columns)
    (game, GameState(nextPlayer = playerA))
  }

  it should "enable computer player to move on one of all dots" in {

    Given("an empty game")
    val playerA = new ComputerPlayer()
    val (game, initState) = getGameAndInitialState(playerA = playerA)
    playerA.setGame(game)
    playerA.setNegascoutGepth(5)

    When("playerA wants to make first move")
    val move = playerA.makeMove(initState)

    Then("move is one of possible dots")
    move.row should be >= 0
    move.row should be < 3
    move.column should be >= 0
    move.column should be < 3
  }

  "Negascout" should "find point that makes closed area" in {

    Given("New game and prepared board")

    val playerA = new ComputerPlayer()
    val playerB = new MockPlayer()

    val (game, initState) = getGameAndInitialState(playerA = playerA, playerB = playerB)
    playerA.setGame(game)
    playerA.setNegascoutGepth(5)

    When("PlayerA is going to move")
    val move = playerA.makeMove(initState +
                                Dot(Point(1, 0), playerA) +
                                Dot(Point(2, 1), playerA) +
                                Dot(Point(1, 2), playerA) +
                                Dot(Point(2, 0), playerB) +
                                Dot(Point(1, 1), playerB) +
                                Dot(Point(2, 2), playerB) )

    Then("It returns point that makes closed area")
    move shouldBe Point(0,1)

  }
}

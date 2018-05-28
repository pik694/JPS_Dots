package dots.model.player

import dots.model.{Dot, Game, Point}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class GameTests extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "A game class"

  "Newly created game" should "be an empty game" in {

    Given("Empty game")
    val playerA = new HumanPlayer
    val playerB = new ComputerPlayer
    val game = new Game(2, 2, playerA, playerB)

    Then("Board should be empty")
    game.playerA should be theSameInstanceAs playerA
    game.playerB should be theSameInstanceAs playerB
    game.matrix should have length 2
    game.matrix(0) should have length 2
    for (i <- 0 until game.matrix.length)
      for (j <- 0 until game.matrix(i).length)
        game.matrix(i)(j) should be theSameInstanceAs null
  }

  it should "enable player to move on an empty dot" in {

    Given("Empty game")
    val playerA = new HumanPlayer
    val playerB = new ComputerPlayer
    val game = new Game(2, 2, playerA, playerB)

    When("PlayerA wanna move on an empty dot")
    val dot = new Dot(Point(0, 0), playerA)

    Then("PlayerA is on en empty dot")
    assert(game.canMove(dot))
    game.move(dot)
    game.matrix(dot.point.row)(dot.point.column) should be theSameInstanceAs playerA
  }

  it should "permit player to move on a non-empty dot" in {

    Given("Game with non-empty dot")
    val playerA = new HumanPlayer
    val playerB = new ComputerPlayer
    val game = new Game(2, 2, playerA, playerB)

    game.move(Dot(Point(0, 0), playerA))

    When("Player wanna move on a non-empty dot")
    val dot = new Dot(Point(0, 0), playerB)

    Then("Movement is permitted")
    assert(!game.canMove(dot))
    game.move(dot)

    And("On non-empty dot is right player")
    game.matrix(dot.point.row)(dot.point.column) should be theSameInstanceAs playerA
  }
}

package dots.model

import dots.model.player.{ComputerPlayer, HumanPlayer}
import dots.model.{Dot, Game, Point}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

import scala.annotation.tailrec

class GameTests extends FlatSpec with Matchers with GivenWhenThen {

  @tailrec
  final def addPoints(game: Game, dots: Seq[Dot]): Game ={
    dots match {
      case Nil => game
      case head :: tail =>{
        game.matrix(head.point.row)(head.point.column) = head.player
        addPoints(game, tail)
      }
    }
  }

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

  "Simple area" should "close border with one dot inside" in {
    Given("Simple area with 8 blue and 1 red dot")

    val playerA = new HumanPlayer
    val playerB = new ComputerPlayer

    val dots = Seq(

      Dot(Point(1,1), playerB),

      Dot(Point(1,2), playerA),
      Dot(Point(2,1), playerA),
      Dot(Point(1,0), playerA)
    )

    val game = addPoints(new Game(10, 10, playerA, playerB), dots)


    When("I add one missing dot")

    game.move(Dot(Point(0,1), playerA))

    Then("Result should be 1-0")

    assert(game.score() == (1,0))

  }

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

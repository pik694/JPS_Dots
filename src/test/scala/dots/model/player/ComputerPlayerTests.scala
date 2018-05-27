package dots.model.player

import org.scalatest.{FlatSpec, Matchers}
import scalafx.scene.paint.Color

class ComputerPlayerTests extends FlatSpec with Matchers {

    "Created red computer player" should "be red" in {
    val computerPlayer = new ComputerPlayer( Color.Red )
      computerPlayer.color should be ( Color.Red )
  }

}

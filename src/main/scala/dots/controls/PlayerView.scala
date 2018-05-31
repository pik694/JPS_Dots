package dots.controls

import dots.model.player.{ComputerPlayer, HumanPlayer, Player}
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

/**
  * Represents player's view
  */
object PlayerView {

  private lazy val humanImage = new Image(getClass.getResource("/icons/human.png").toString, 16, 16, true, true)
  private lazy val computerImage = new Image(getClass.getResource("/icons/computer.png").toString, 16, 16, true, true)

  private var playerA: Player = null
  private var playerB: Player = null


  def image(player: HumanPlayer): Image = {
    humanImage
  }

  def image(player: ComputerPlayer): Image = {
    computerImage
  }

  def color(player: Player): Color = {
    if (player == playerA) {
      Color.Red
    }
    else if (player == playerB) {
      Color.Blue
    }
    else {
      if (playerA == null) {
        playerA = player
      }
      else {
        playerB = player
      }
      color(player)
    }
  }


}

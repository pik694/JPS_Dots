package dots.model.player

import dots.model.Point
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

case class ComputerPlayer(color: Color) extends Player {

//  override val photo: Image = new Image(getClass.getResource("/icons/computer.png").toString, 16, 16, true, true)

  override def makeMove(): Point = {
    Point(0, 0)
  }

}

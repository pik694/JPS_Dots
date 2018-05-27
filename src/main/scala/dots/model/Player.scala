package dots.model

import scalafx.scene.image.Image
import scalafx.scene.paint.Color

trait Player {
  val photo: Image
  val color: Color

  def makeMove(): Point

}

case class HumanPlayer(color: Color) extends Player{
  override val photo: Image = new Image(getClass.getResource("/icons/human.png").toString, 16, 16, true, true)
  override def makeMove(): Point = {
    Point(0,0)
  }

}
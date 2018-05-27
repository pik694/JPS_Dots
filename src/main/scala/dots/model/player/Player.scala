package dots.model.player

import dots.model.Point
import scalafx.scene.image.Image
import scalafx.scene.paint.Color

trait Player {
  val photo: Image
  val color: Color

  def makeMove(): Point

}

package dots.model.player

import dots.model.Point
import scalafx.scene.paint.Color

trait Player {
  def makeMove(): Point

}

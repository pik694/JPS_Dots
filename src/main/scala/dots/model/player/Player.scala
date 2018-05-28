package dots.model.player

import dots.model.Point

trait Player {
  def makeMove(): Point
  def acceptMove(point: Point){}
}

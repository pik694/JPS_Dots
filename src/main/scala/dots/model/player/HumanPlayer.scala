package dots.model.player

import dots.model.{Dot, Game, Point}


class HumanPlayer extends Player {
  override def acceptMove(point: Point): Unit = {
    Game.move(Dot(point, this))
  }

  override def makeMove(): Point = {
    null
  }

}
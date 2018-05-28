package dots.model.player

import dots.model.Point

class ComputerPlayer extends Player {

  //  override val photo: Image = new Image(getClass.getResource("/icons/computer.png").toString, 16, 16, true, true)

  override def makeMove(): Point = {
    Point(0, 0)
  }

}

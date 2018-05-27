package dots.model

import dots.model.player.Player
import scalafx.beans.property.ObjectProperty

object Game {

  val playerA_ = new ObjectProperty[Player]()
  val playerB_ = new ObjectProperty[Player]()

  val score_ = new ObjectProperty[(Int, Int)]()

  val move_ = new ObjectProperty[Player]()


}


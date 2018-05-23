package hello

import dots.gui.Board
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color

object HelloWorld extends JFXApp {

  lazy val board =  new Board(10,10)

  stage = new PrimaryStage {
    title = "Kropki : NegaScout"
    scene = new Scene(1200, 1200) {
      root = board
    }
  }

  board.addPoint(1,3, Color.web("GREEN", 1))
  board.addPoint(1,1, Color.web("GREEN", 1))
  board.addPoint(4,2, Color.web("GREEN", 1))
  board.addPoint(2,3, Color.web("RED", 1))
  board.addPoint(2,4, Color.web("RED", 1))
  board.addPoint(3,4, Color.web("RED", 1))

}
package hello

import dots.gui.{Board, MainScene}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Separator}
import scalafx.scene.layout._
import scalafx.scene.paint.Color

object HelloWorld extends JFXApp {

  lazy val board = new Board(10, 10){hgrow = Priority.Always}

  stage = new PrimaryStage {
    title = "Kropki : NegaScout"
    scene = MainScene
  }

//  board.addPoint(1, 3, Color.web("GREEN", 1))
//  board.addPoint(1, 1, Color.web("GREEN", 1))
//  board.addPoint(4, 2, Color.web("GREEN", 1))
//  board.addPoint(2, 3, Color.web("RED", 1))
//  board.addPoint(2, 4, Color.web("RED", 1))
//  board.addPoint(3, 4, Color.web("RED", 1))

}
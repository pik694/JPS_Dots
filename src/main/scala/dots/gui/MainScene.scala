package dots.gui


import javafx.event.ActionEvent
import scalafx.beans.property.BooleanProperty
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, Priority, VBox}
import scalafx.scene.paint.Color

object MainScene extends Scene(1000, 800) {

  val SPACING = 10

  lazy val playerAColor = Color.web("RED", 1)
  lazy val playerBColor = Color.web("BLUE", 1)

  private val isPlaying = new BooleanProperty()

  GameSettingsPane.disable <== isPlaying

  var board = new Board(10, 10) {
    hgrow = Priority.Always
    vgrow = Priority.Always
  }

  val startButton = new Button("Start")

  startButton.onAction = (_: ActionEvent) => {
    isPlaying() = true
  }

  root = new HBox(SPACING) {
    padding = Insets(SPACING)
    children = Seq(
      new VBox(SPACING) {
        padding = Insets(20)
        children = Seq(
          GameSettingsPane,
          new Separator,
          startButton
        )
      },
      new Separator {
        orientation = Orientation.Vertical
      },
      board
    )
  }


}

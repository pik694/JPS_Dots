package dots.gui

import dots.gui.MainScene.{getClass, _}
import scalafx.scene.control.{Separator, Spinner, ToggleButton, ToggleGroup}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{GridPane, Pane, VBox}
import scalafx.scene.text.Text

object GameSettingsPane extends Pane {

  private lazy val humanImage: Image = new Image(getClass.getResource("/icons/human.png").toString, 16, 16, true, true)
  private lazy val computerImage: Image = new Image(getClass.getResource("/icons/computer.png").toString, 16, 16, true, true)


  private val playerAToggleGroup = new ToggleGroup()
  private val playerBToggleGroup = new ToggleGroup()


  children = new VBox(MainScene.SPACING) {
    children = Seq(
      new Text {
        text = "Ustawienia gry"
        style = "-fx-font-size: 24; fx-font-weight: bold"
      },
      new Separator,
      new Text {
        text = "Pole gry"
        style = "-fx-font-size: 18; fx-font-weight: bold"
      },
      new GridPane {
        vgap = SPACING / 2
        hgap = SPACING / 2
        add(new Text("Rzędy"), 0, 0)
        add(new Text("Kolumny"), 0, 1)
        add(new Spinner[Int](1, 256, 10), 1, 0)
        add(new Spinner[Int](1, 256, 10), 1, 1)
      },
      new Separator,
      new Text {
        text = "Gracze"
        style = "-fx-font-size: 18; fx-font-weight: bold"
      },

      new GridPane {
        vgap = SPACING / 2
        hgap = SPACING / 2
        add(new Text("Gracz A"){
          fill = MainScene.playerAColor
        }, 0, 0, 2, 1)
        add(new Text("Gracz B"){
          fill = MainScene.playerBColor
        }, 2, 0, 2, 1)
        add(new ToggleButton {
          toggleGroup = playerAToggleGroup
          graphic = new ImageView(humanImage)
        }, 0, 1)
        add(new ToggleButton {
          toggleGroup = playerAToggleGroup
          graphic = new ImageView(computerImage)
        }, 1, 1)
        add(new ToggleButton {
          toggleGroup = playerBToggleGroup
          graphic = new ImageView(humanImage)
        }, 2, 1)
        add(new ToggleButton {
          graphic = new ImageView(computerImage)
          toggleGroup = playerBToggleGroup
        }, 3, 1)
      }
    )
  }
}

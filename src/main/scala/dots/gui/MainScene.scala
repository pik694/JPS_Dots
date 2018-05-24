package dots.gui

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{GridPane, HBox, Priority, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text

object MainScene extends Scene(1200, 1200) {

  val SPACING = 10
  lazy val humanImage: Image = new Image(getClass.getResource("/icons/human.png").toString, 16, 16, true, true)
  lazy val computerImage: Image = new Image(getClass.getResource("/icons/computer.png").toString, 16, 16, true, true)
  lazy val playerAColor = Color.web("RED", 1)
  lazy val playerBColor = Color.web("BLUE", 1)


  var board = new Board(10, 10) {
    hgrow = Priority.Always
    vgrow = Priority.Always
  }

  val playerAToggleGroup = new ToggleGroup()
  val playerBToggleGroup = new ToggleGroup()

  root = new HBox(SPACING) {
    padding = Insets(20)
    children = Seq(
      new VBox(SPACING) {
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
              fill = playerAColor
            }, 0, 0, 2, 1)
            add(new Text("Gracz B"){
              fill = playerBColor
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
          },

          new Separator,

          new Button("Start"),
          new Separator,
          new Text {
            text = "Rozgrywka"
            style = "-fx-font-size: 18; fx-font-weight: bold"
          },
          new GridPane {

          }
        )
      },
      board
    )
  }


}

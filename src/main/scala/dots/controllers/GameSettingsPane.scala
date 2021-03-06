package dots.controllers

import dots.model.player.{ComputerPlayer, HumanPlayer, Player}
import scalafx.geometry.Insets
import scalafx.scene.control._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{GridPane, Pane, VBox}
import scalafx.scene.text.Text

/**
  * Singleton game setting window
  * Shown at the beginning of the game
  */
object GameSettingsPane extends Pane {

  private lazy val humanImage: Image = new Image(getClass.getResource("/icons/human.png").toString, 16, 16, true, true)
  private lazy val computerImage: Image = new Image(getClass.getResource("/icons/computer.png").toString, 16, 16, true, true)

  private val initDepth = 2

  private val playerAToggleGroup = new ToggleGroup()
  private val playerBToggleGroup = new ToggleGroup()

  private val columnSpinner = new Spinner[Int](1, 256, 10)
  private val rowSpinner = new Spinner[Int](1, 256, 10)

  private val depthSpinner = new Spinner[Int](0, 256, initDepth)

  private val playerAComputer = new ComputerPlayer
  private val playerBComputer = new ComputerPlayer

  playerAComputer.setNegascoutGepth(initDepth)
  playerBComputer.setNegascoutGepth(initDepth)

  depthSpinner.value.onChange((_, _, newValue) => {
    playerAComputer.setNegascoutGepth(newValue)
    playerBComputer.setNegascoutGepth(newValue)
  })

  children = new VBox(10) {
    padding = Insets(20)

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
        vgap = 5
        hgap = 5
        add(new Text("Rzędy"), 0, 0)
        add(new Text("Kolumny"), 0, 1)
        add(rowSpinner, 1, 0)
        add(columnSpinner, 1, 1)
      },
      new Separator,
      new Text {
        text = "Gracze"
        style = "-fx-font-size: 18; fx-font-weight: bold"
      },

      new GridPane {
        vgap = 5
        hgap = 5
        add(new Text("Gracz A"), 0, 0, 2, 1)
        add(new Text("Gracz B"), 2, 0, 2, 1)
        add(new ToggleButton {
          toggleGroup = playerAToggleGroup
          toggleGroup().selectToggle(this.asInstanceOf[Toggle])
          userData = new HumanPlayer;
          graphic = new ImageView(humanImage)
        }, 0, 1)
        add(new ToggleButton {
          toggleGroup = playerAToggleGroup
          userData = playerAComputer
          graphic = new ImageView(computerImage)
        }, 1, 1)
        add(new ToggleButton {
          toggleGroup = playerBToggleGroup
          toggleGroup().selectToggle(this.asInstanceOf[Toggle])
          userData = new HumanPlayer
          graphic = new ImageView(humanImage)
        }, 2, 1)
        add(new ToggleButton {
          graphic = new ImageView(computerImage)
          userData = playerBComputer
          toggleGroup = playerBToggleGroup

        }, 3, 1)
      },

      new Separator,
      new Text {
        text = "Ustawienia komputera"
        style = "-fx-font-size: 18; fx-font-weight: bold"
      },
      new Text("Głębokość przeszukiwania:"),
      depthSpinner
    )
  }


  def playerA: Player = playerAToggleGroup.selectedToggle.value.getUserData.asInstanceOf[Player]

  def playerB: Player = playerBToggleGroup.selectedToggle.value.getUserData.asInstanceOf[Player]

  def rows: Int = rowSpinner.value()

  def columns: Int = columnSpinner.value()

  def depth: Int = depthSpinner.value()

}

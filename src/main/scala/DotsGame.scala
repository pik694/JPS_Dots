package dots

import dots.gui.{GameSettings, GameSettingsPane, MainScene}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.paint.Color


object DotsGame extends JFXApp {

  def showSettingsDialog: GameSettings = {
    val dialog = new Dialog[GameSettings]() {
      initOwner(stage)
      title = "Ustawienia gry"
    }
    val startButtonType = new ButtonType("Start", ButtonData.OKDone)
    dialog.dialogPane().getButtonTypes.addAll(startButtonType, ButtonType.Cancel)

    val startButton = dialog.dialogPane().lookupButton(startButtonType)
    startButton.setDisable(true)

    dialog.resultConverter = dialogButton =>
      if (dialogButton == startButtonType)
        GameSettings(PlayerA(Color.web("RED")), PlayerA(Color.web("RED")), 1, 1)
      else null

    dialog.dialogPane().setContent(GameSettingsPane)

    dialog.showAndWait() match {
      case Some(GameSettings(playerA, playerB, columns, rows)) => GameSettings(playerA, playerB, columns, rows)
      case _ => null
    }

  }

  stage = new PrimaryStage {
    title = "Kropki : NegaScout"
    scene = MainScene
  }

  val settings = showSettingsDialog

}
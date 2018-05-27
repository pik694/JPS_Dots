package dots

import javafx.{fxml => jfxf, scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

object DotsGame extends JFXApp {

  //  def showSettingsDialog: GameSettings = {
  //    val dialog = new Dialog[GameSettings]() {
  //      initOwner(stage)
  //      title = "Ustawienia gry"
  //    }
  //    val startButtonType = new ButtonType("Start", ButtonData.OKDone)
  //    dialog.dialogPane().getButtonTypes.addAll(startButtonType, ButtonType.Cancel)
  //
  //    val startButton = dialog.dialogPane().lookupButton(startButtonType)
  //
  //    dialog.resultConverter = dialogButton =>
  //      if (dialogButton == startButtonType)
  //        GameSettings(
  //          GameSettingsPane.playerA,
  //          GameSettingsPane.playerB,
  //          GameSettingsPane.columns,
  //          GameSettingsPane.rows)
  //      else null
  //
  //    dialog.dialogPane().setContent(GameSettingsPane)
  //
  //    dialog.showAndWait() match {
  //      case Some(GameSettings(playerA, playerB, columns, rows)) => GameSettings(playerA, playerB, columns, rows)
  //      case _ => null
  //    }
  //
  //  }


  val resource = getClass.getResource("/views/MainScene.fxml")
  private val root: jfxs.Parent = jfxf.FXMLLoader.load(resource)

  stage = new PrimaryStage {
    title = "Kropki : NegaScout"
    scene = new Scene(root)
  }

  //  val settings = showSettingsDialog
  //
  //  if (settings == null) {
  //    System.exit(0)
  //  }


  //  MainController.setBoardSize(settings.rows, settings.columns)

}
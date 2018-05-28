package dots.controllers

import java.net.URL
import java.util

import dots.controls.{DotView, PlayerView}
import dots.model.player.HumanPlayer
import dots.model.{Dot, Game}
import javafx.scene.input.MouseEvent
import javafx.scene.{text => jfxt}
import javafx.{fxml => jfxf}
import scalafx.scene.paint.Color

class MainController extends jfxf.Initializable {

  @jfxf.FXML
  private var playerAText: jfxt.Text = null

  @jfxf.FXML
  private var playerBText: jfxt.Text = null

  @jfxf.FXML
  private var playerAResult: jfxt.Text = null

  @jfxf.FXML
  private var playerBResult: jfxt.Text = null

  @jfxf.FXML
  private var board: dots.controls.BoardControl = null

  protected def initialize(url: URL, rb: util.ResourceBundle) {

    MainController(this)

    Game.playerA.onChange((_, _, newVal) => {
      playerAText.setFill(PlayerView.color(newVal))
    })
    Game.playerB.onChange((_, _, newVal) => {
      playerBText.setFill(PlayerView.color(newVal))
    })
    Game.nextMovePlayer.onChange((_, _, newVal) => {
      playerAText.setUnderline(newVal == Game.playerA())
      playerBText.setUnderline(newVal == Game.playerB())
    })

    Game.score.onChange((_, _, newVal) => {
      playerAResult.setText(newVal._1.toString)
      playerBResult.setText(newVal._2.toString)
    })

    Game(10, 10, new HumanPlayer, new HumanPlayer)

  }

  protected def mouseClicked(event: MouseEvent): Unit = {

    val point = board.translate(event)

    if (point != null) {
      Game.nextMovePlayer().acceptMove(point)
    }

  }

  def connectDots(dots: Seq[Dot]) {

  }

  def addDot(dot: Dot): Unit = {
    board.drawDot(new DotView(dot.point.row, dot.point.column, PlayerView.color(dot.player)))
  }
}

object MainController {

  private var delegate: MainController = null

  def apply(controller: MainController): Unit = {
    delegate = controller
  }


  def connectDots(dots: Seq[Dot]): Unit = {
    delegate.connectDots(dots)
  }

  def addDot(dot: Dot): Unit = {
    delegate.addDot(dot)
  }

}

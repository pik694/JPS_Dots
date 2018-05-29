package dots.controllers

import java.net.URL
import java.util

import dots.controls.{DotView, PlayerView}
import dots.model.{Dot, Game}
import javafx.scene.input.MouseEvent
import javafx.scene.{text => jfxt}
import javafx.{fxml => jfxf}

import scala.annotation.tailrec

private[controllers] class MainController extends jfxf.Initializable {

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
  }

  protected def mouseClicked(event: MouseEvent): Unit = {

    val point = board.translate(event)

    if (point != null) {
      Game.nextMovePlayer().acceptMove(point)
    }

  }

  def connectDots(dots: Seq[Dot]) {
    @tailrec
    def drawLines(dots: Seq[Dot]): Dot ={
      if (dots.size == 1) dots.head
      else {
        val x :: tail = dots
        board.drawLine(x.point, tail.head.point, PlayerView.color(x.player).delegate)
        drawLines(tail)
      }
    }

    val last = drawLines(dots)
    board.drawLine(dots.head.point, last.point, PlayerView.color(last.player).delegate)

  }

  def addDot(dot: Dot): Unit = {
    board.drawDot(new DotView(dot.point.row, dot.point.column, PlayerView.color(dot.player)))
  }

  def setBoardSize(rows: Int, columns : Int): Unit ={
    board.setRows(rows)
    board.setColumns(columns)
  }
}

object MainController {

  private var delegate: MainController = null

  def apply(controller: MainController): Unit = {
    delegate = controller
  }

  def setBoardSize(rows: Int, columns : Int): Unit ={
    delegate.setBoardSize(rows, columns)
  }

  def connectDots(dots: Seq[Dot]): Unit = {
    if (delegate != null) {
      delegate.connectDots(dots)
    }
  }

  def addDot(dot: Dot): Unit = {
    if (delegate != null) {
      delegate.addDot(dot)
    }
  }

}

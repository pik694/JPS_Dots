package dots.controllers

import java.net.URL
import java.util

import dots.controls.{DotView, PlayerView}
import dots.model.{Dot, Game}
import javafx.scene.input.MouseEvent
import javafx.scene.{text => jfxt}
import javafx.{fxml => jfxf}

import scala.annotation.tailrec

/**
  * Main controller managing app
  */
private[controllers] class MainController extends jfxf.Initializable {

  @jfxf.FXML
  private var playerAText: jfxt.Text = _

  @jfxf.FXML
  private var playerBText: jfxt.Text = _

  @jfxf.FXML
  private var playerAResult: jfxt.Text = _

  @jfxf.FXML
  private var playerBResult: jfxt.Text = _

  @jfxf.FXML
  private var board: dots.controls.BoardControl = _

  /**
    * Initialize app
    * @param url
    * @param rb
    */
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

  /**
    * Passes mouse event to the model
    * @param event
    */
  protected def mouseClicked(event: MouseEvent): Unit = {

    val point = board.translate(event)

    if (point != null) {
      Game.nextMovePlayer().acceptMove(point)
    }

  }

  /**
    * Function drawing connections between dots
    * Visualize closed area
    * @param dots sequence of dots
    */
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

  /**
    * Function drawing dot on the screen
    * @param dot to be drawn
    */
  def addDot(dot: Dot): Unit = {
    board.drawDot(new DotView(dot.point.row, dot.point.column, PlayerView.color(dot.player)))
  }

  /**
    * Sets board size
    * @param rows
    * @param columns
    */
  def setBoardSize(rows: Int, columns : Int): Unit ={
    board.setRows(rows)
    board.setColumns(columns)
  }
}

/**
  * Singleton of Main Controller
  */
object MainController {

  private var delegate: MainController = _

  /**
    * Initialize main controller
    * @param controller
    */
  def apply(controller: MainController): Unit = {
    delegate = controller
  }

  /**
    * Calls main controller function setting board size
    * @param rows
    * @param columns
    */
  def setBoardSize(rows: Int, columns : Int): Unit ={
    delegate.setBoardSize(rows, columns)
  }

  /**
    * Calls main controller function drawing closed area
    * @param dots sequence of dots to be connected
    */
  def connectDots(dots: Seq[Dot]): Unit = {
    if (delegate != null) {
      delegate.connectDots(dots)
    }
  }

  /**
    * Calls main controller function drawing dot
    * @param dot to be drawn
    */
  def addDot(dot: Dot): Unit = {
    if (delegate != null) {
      delegate.addDot(dot)
    }
  }

}

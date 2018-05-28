package dots.controllers

import java.net.URL
import java.util

import dots.controls.DotView
import dots.model.Dot
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

  def initialize(url: URL, rb: util.ResourceBundle) {
//    playerAText.setFill(playerAColor)
//    playerBText.setFill(playerBColor)

    playerAText.setUnderline(true)

    board.drawDot(new DotView(1,2,Color.Red))

  }

  def connectDots(dots: Seq[Dot]){

  }

  def addDot(dot: Dot): Unit ={

  }
}

package dots.controllers

import java.net.URL
import java.util

import javafx.scene.{text => jfxt}
import javafx.{fxml => jfxf}
import scalafx.scene.paint.Color

class MainController extends jfxf.Initializable {

  private val playerAColor = Color.Red
  private val playerBColor = Color.Blue

  @jfxf.FXML
  private var playerAText: jfxt.Text = null

  @jfxf.FXML
  private var playerBText: jfxt.Text = null

  @jfxf.FXML
  private var playerAResult: jfxt.Text = null

  @jfxf.FXML
  private var playerBResult: jfxt.Text = null

  def initialize(url: URL, rb: util.ResourceBundle) {
    playerAText.setFill(playerAColor)
    playerBText.setFill(playerBColor)

    playerAText.setUnderline(true)



  }


}

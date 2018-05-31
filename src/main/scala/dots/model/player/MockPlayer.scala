package dots.model.player
import dots.model.Point
import dots.model.utils.game.GameState

/**
  * Mocked player
  * Created on test necessity
  * @param dots sequence of dots to be added during test cases
  */
private[model] class MockPlayer(private var dots: List[Point]) extends Player {

  def this() = this(List.empty)

  /**
    * Main player's action
    * @param gameState actual game state
    * @return next move
    */
  override def makeMove(gameState: GameState): Point = {
    val head = dots.head
    dots = dots.tail
    head
  }

  /**
    * Main player's action
    * @return next move
    */
  def makeMove: Point = {
    if (dots.isEmpty) return null
    val head = dots.head
    dots = dots.tail
    head
  }
}

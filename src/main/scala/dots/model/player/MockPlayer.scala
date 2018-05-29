package dots.model.player
import dots.model.Point
import dots.model.utils.game.GameState

private[model] class MockPlayer(private var dots: List[Point]) extends Player {

  def this() = this(List.empty)

  override def makeMove(gameState: GameState): Point = {
    val head = dots.head
    dots = dots.tail
    head
  }
}

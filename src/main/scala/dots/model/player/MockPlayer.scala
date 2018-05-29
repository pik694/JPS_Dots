package dots.model.player
import dots.model.Point

private[model] class MockPlayer(private var dots: List[Point]) extends Player {

  def this() = this(List.empty)

  override def makeMove(): Point = {
    val head = dots.head
    dots = dots.tail
    head
  }
}

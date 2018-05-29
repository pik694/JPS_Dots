package dots.model.utils

import dots.model.Point

private[model] case class Hull(dots: Seq[Point]) {

  def isEmpty: Boolean = dots.isEmpty

  def nonEmpty: Boolean = dots.nonEmpty

  def head: Point = {
    if (isEmpty) return null
    val head :: _ = dots
    head
  }

  def tail: Hull = {
    if (isEmpty) return Hull(Seq.empty)
    val _ :: tail = dots
    Hull(tail)
  }

  def contains(point: Point): Boolean = {
    dots.contains(point)
  }
}


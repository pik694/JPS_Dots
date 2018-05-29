package dots.model.utils

import dots.model.Dot

private[model] case class Hull(dots: Seq[Dot]) {

  def isEmpty: Boolean = dots.isEmpty

  def nonEmpty: Boolean = dots.nonEmpty

  def head: Dot = {
    if (isEmpty) return null
    val head :: _ = dots
    head
  }

  def tail: Hull = {
    if (isEmpty) return Hull(Seq.empty)
    val _ :: tail = dots
    Hull(tail)
  }

  def contains(dot: Dot): Boolean ={
    dots.contains(dot)
  }
}


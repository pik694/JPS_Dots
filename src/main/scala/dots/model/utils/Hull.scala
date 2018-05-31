package dots.model.utils

import dots.model.Point

/**
  * Class that represents hull
  * @param dots sequence of points creating hull
  */
private[model] case class Hull(dots: Seq[Point]) {

  /**
    * Check if hull is empty
    * @return true if hull is empty
    */
  def isEmpty: Boolean = dots.isEmpty

  /**
    * Check if hull is nonempty
    * @return true if hull is nonempty
    */
  def nonEmpty: Boolean = dots.nonEmpty

  /**
    * Function to return head of hull
    * @return first point of hull or null if collection is empty
    */
  def head: Point = {
    if (isEmpty) return null
    val head :: _ = dots
    head
  }


  /**
    * Function to return tail of hull
    * @return every point of hull without head
    */
  def tail: Hull = {
    if (isEmpty) return Hull(Seq.empty)
    val _ :: tail = dots
    Hull(tail)
  }

  /**
    * Check if hull contains given point
    * @param point point to check
    * @return  true if given point is one of creating hull
    */
  def contains(point: Point): Boolean = {
    dots.contains(point)
  }
}


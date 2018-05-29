package dots.model.utils

import dots.model.{Dot, Point}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class HullSpec extends FlatSpec with Matchers with GivenWhenThen {

  "Empty hull" should "empty" in {
    Given("Empty hull")
    val hull = Hull(Seq.empty)

    Then("Hull should be empty")

    hull.isEmpty should be (true)


  }

  it should "have no head" in {
    Given("Empty hull")
    val hull = Hull(Seq.empty)

    Then("Head of the hull should be null")

    hull.head should be (null)
  }

  it should "have empty tail" in {
    Given("Empty hull")
    val hull = Hull(Seq.empty)
    When("I get tail of the hull")

    val tail =  hull.tail

    Then("Tail of the hull should be empty")

    tail.isEmpty should be (true)
  }

  "Hull with one element" should "not be empty" in {
    Given("Hull with one element")

    val dot = Dot(Point(0,0), null)

    val hull = Hull(Seq(dot.point))

    Then("Its head should not be empty")

    hull.head should be (dot.point)

    Then("Its tail should be empty")

    hull.tail.isEmpty should be (true)
  }


  "Hull with several elements" should "not be empty" in {

    Given("Hull with multiple elements")

    val dot = Dot(Point(0,0), null)
    val hull = Hull(Seq(dot.point, dot.point, dot.point))

    Then("Head should be a valid dot")

    hull.head should be (dot.point)

    When("I get hull's tail")

    val tail = hull.tail

    Then("Tail should be non empty")

    tail.nonEmpty should be (true)


  }

}

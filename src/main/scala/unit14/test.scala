package unit14

import unit10.Element

object test {
  private def widen(w:Int) = {
    if (w <= 10) 100
    else {
      println("sakndjknwkjdw")
      10000
    }.ensuring (w >= 100)
  }

  def main(args: Array[String]): Unit = {
    println(widen(10))
  }


}

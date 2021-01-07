package unit16

object ListTest {
  def isort(xs: List[Int]): List[Int] = {
    if (xs.isEmpty) Nil else {
      println(xs.head, xs.tail)
      insert(xs.head, isort(xs.tail))
    }
  }

  def insert(x: Int, xs: List[Int]): List[Int] = {
    if (xs.isEmpty || x <= xs.head) x :: xs
    else xs.head :: insert(x, xs.tail)
  }

  def append[T](xs:List[T],ys:List[T]):List[T] = {
    xs match {
      case List() => ys
      case x::xsl => x :: append(xsl,ys)//6666
    }
  }









  def main(args: Array[String]): Unit = {
//    println(isort(List(3, 4, 2, 6)))
//    println(insert(4, List(2, 6)))
//    val List(a, b, c) = List("aa", "bb", "cc")
    print(append(List(1,2,3),List(4,5,6)))
  }


}

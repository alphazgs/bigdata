package unit06

object test {
  def main(args: Array[String]): Unit = {
    //隐式转换
    implicit def int2rational(n: Int) = {
      new Rational(n)
    }

    val a = new Rational(4, 8)
    val c = 5
    val b = new Rational(10, 20)
    println(c+a) //因为有了隐式转换，所以c:Int才有了 Rational中的 + 方法

    val fg = new Rational(4, 5)
    val g2 = new Rational(5, 6)
    val li = List(fg, g2)
    println(li.sorted)//因为继承了Ordered特质，等于实现 >= ,<,>,<=方法
  }

}

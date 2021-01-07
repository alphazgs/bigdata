package unit12

/*
12.1 Ordered 特质
 */


class MyInt(n:Int) extends Ordered[MyInt]{ //如果需要显示对象的比较<,>,<=,>=等操作，可以在类里面混入Ordered特质
  private val number = n
  override def compare(that: MyInt): Int = this.number-that.number //需要实现Ordered特质的compare方法
  //但是Ordered特质并没有重写equals方法，要比较相等时还是要重写equals方法
}




object TraitDemo1 {
  def main(args: Array[String]): Unit = {
    val myInt1 = new MyInt(1)
    val myInt2 = new MyInt(2)
    println(myInt1 >= myInt2)
    println(myInt1 > myInt2)
    println(myInt1 <= myInt2)
    println(myInt1 < myInt2)
  }

}

package unit09

import java.io.{File, PrintWriter}

object FileMatcher {
  /*
  9.控制抽象
   */
  //1.减少代码重复
  private def filesHere = (new File(".")).listFiles

  //  def filesEnding(query:String) = for (file <- filesHere; if file.getName.endsWith(query)) yield file
  //  def filesContains...
  //  def filesStartWith...


  def filesMatching(filter: String => Boolean) = for (file <- filesHere; if filter(file.getName)) yield file

  //将函数作为参数传递,filesEnding方法是主体结构在做循环
  filesMatching(_.endsWith("dsdw"))
  filesMatching(_.contains("dnjw"))
  filesMatching(_.startsWith("djebhj"))

  //2、简化调用方代码
  def containsNeg(nums: List[Int]): Boolean = {
    var exists = false
    for (num <- nums)
      if (num < 0)
        exists = true
    exists
  }

  //使用scala类库的方法简化调用方代码
  def containsNeg1(nums: List[Int]): Boolean = {
    nums.exists(_ < 0)
  }

  //3、柯里化
  def curriedSum(x: Int)(y: Int) = x + y

  val onePlus = curriedSum(1) _


  //4、编写新的控制结构
  def twice(op: Double => Double, x: Double) = op(op(x))

  twice(_ + 1, 5)

  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  withPrintWriter(
    new File("date.txt"),
    _.println(new java.util.Date)
  )

  //5、传名参数
  var assertionsEnabled = true

//  def myAssert(predicate: () => Boolean) = if (assertionsEnabled && !predicate()) throw new AssertionError
  //predicate为传名参数
  def myAssert(predicate: => Boolean):Unit = if (assertionsEnabled && !predicate) throw new AssertionError
  def boolAssert(predicate:Boolean):Unit = if (assertionsEnabled && !predicate) throw new AssertionError
  //这两种方式都是合法的，但是会有一个显著的区别就是，boolAssert的参数类型为Boolean，在boolAssert(5 > 3)圆括号中的表达式将“先于”对boolAssert的调用被求值。
  //而myAssert中并不会。

  def main(args: Array[String]): Unit = {
    myAssert( 5 > 3)
    boolAssert(5> 3)
  }


}

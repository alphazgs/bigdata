package unit10

import Element.elem


abstract class Element {
  /*
  抽象类
   */
  def contents: Array[String] //抽象方法

  //无参方法,推荐的做法是对于没有参数且只通过读取所在对象字段的方式访问可变状态(确切地说不改变状态)
  // 的情况下尽量使用无参方法
  def width: Int = contents(0).length

  // val height:Int = contents.length
  def height: Int = contents.length

  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    elem(this1.contents ++ that1.contents)
  }


  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
      for ((line1, line2) <- this1.contents zip that1.contents)
        yield line1 + line2)
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }

  override def toString: String = contents mkString "\n"
}


//工厂对象
object Element {
  private class ArrayElement(val contents: Array[String]) extends Element {} //参数化字段

  private class LineElement(s: String) extends Element {
    val contents = Array(s)
    override def width: Int = s.length
    override def height: Int = 1
  }

  private class UniformElement(ch: Char,override val width: Int,override val height: Int) extends Element {
    private val line = ch.toString * width
    def contents: Array[String] = Array.fill(height)(line)
  }

  def elem(contents: Array[String]): Element = new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element = new UniformElement(chr, width, height)

  def elem(line: String): Element =new LineElement(line)
}

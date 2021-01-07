package unit06

class Rational(n: Int, d: Int)  extends  Ordered[Rational] {
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val numer: Int = n / g//字段，只有写成字段，+方法中的that.denom才能调用。
  val denom: Int = d / g

  def this(n: Int) = this(n, 1)//辅助构造方法

  override def toString: String = numer + "/" + denom

  def +(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)

  def +(i:Int):Rational = new Rational(numer+i*denom,denom)//方法重载

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)//私有方法

  override def compare(that: Rational): Int = (this.numer * that.denom) - (that.numer*this.denom)

}

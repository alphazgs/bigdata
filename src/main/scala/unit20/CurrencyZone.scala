package unit20

abstract class CurrencyZone {//用来包含的类
  type Currency <: AbstractCurrency//抽象类型,用来限定不同地区的不同货币，而且防止不同的货币种类混杂，比如+方法，必须要是同种货币才能相加。
  //用于一些子类型之间存在关系的场景

  def make(x: Long): Currency //工厂方法,避免直接创建抽象类型的实例.放在外面是为了防止类本身能够重复调用。

  abstract class AbstractCurrency {
    val amount: Long

    def designation: String

    def +(that: Currency): Currency =
      make(this.amount + that.amount)

    def *(x: Double): Currency =
      make((this.amount * x).toLong)

    def from(other: CurrencyZone#AbstractCurrency): Currency =
      make(math.round(other.amount.toDouble * Converter.exchangeRate(other.designation)(this.designation)))

    private def decimals(n: Long): Int = if (n == 1) 0 else 1 + decimals(n / 10)

    override def toString: String = ((amount.toDouble / CurrencyUnit.amount.toDouble)
      formatted ("%." + decimals(CurrencyUnit.amount) + "f") + " " + designation)

  }

  val CurrencyUnit: Currency
}

object Converter {
  var exchangeRate = Map(
    "USD" -> Map("USD" -> 1.0, "EUR" -> 0.7596, "JPY" -> 1.211, "CHF" -> 1.223),
    "EUR" -> Map("USD" -> 1.316, "EUR" -> 1.0, "JPY" -> 1.594, "CHF" -> 1.623),
    "JPY" -> Map("USD" -> 0.8257, "EUR" -> 0.6272, "JPY" -> 1.0, "CHF" -> 1.018),
    "CHF" -> Map("USD" -> 0.8108, "EUR" -> 0.6160, "JPY" -> 0.982, "CHF" -> 1.0)
  )
}


object US extends CurrencyZone {

  abstract class Dollar extends AbstractCurrency {
    //抽象类的继承并不要全部实现父类的抽象
    override def designation: String = "USD"
  }

  override type Currency = Dollar

  override def make(cents: Long): Dollar = new Dollar {
    override val amount: Long = cents
  }


  val Cent = make(1)
  val Dollar = make(100)
  val CurrencyUnit = Dollar
}

object Europe extends CurrencyZone {
  abstract class Euro extends AbstractCurrency{
    override def designation: String = "EUR"
  }

  override type Currency = Euro
  def make(cents:Long) = new Euro {
    override val amount: Long = cents
  }

  val Cent = make(1)
  val Euro = make(100)
  val CurrencyUnit = Euro
}











object Demo {

  def main(args: Array[String]): Unit = {
    println(US.Dollar from Europe.Euro * 100)
    val d1 = US.make(100)





  }


}
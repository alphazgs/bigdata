package unit20

object Main {

  abstract class AbstractCurrency{
    type Currency <: AbstractCurrency
    val amount:Long
    def designation:String

    override def toString: String = amount +" "+designation
    def +(that:Currency):Currency// = new Currency{override val amount: Long = this.amount+that.amount}
    def *(x:Double):Currency
  }

  abstract class Dollar extends AbstractCurrency{
    override type Currency = Dollar

    override def designation: String = "USD"
  }









  def main(args: Array[String]): Unit = {
//    for (d <- Direction.values) println(d)




  }







}

package unit15

sealed abstract class Expr //密封类，密封类除了在同一个文件中定义的子类之外，不能添加新的子类。
case class Var(name: String) extends Expr

case class Number(num: Double) extends Expr

case class UnOp(operator: String, arg: Expr) extends Expr

case class BinOp(operator: String,
                 left: Expr, right: Expr) extends Expr

object Test {


  def sealMatch(expr: Expr) = expr match {
    //密封类如果匹配中漏掉了某个子类，会得到编译器警告
    case Number(_) => println("a Number")
    case Var(_) => println("a Var")
    case UnOp(_,_) => println("a UnOp")
    case BinOp(_,_,_) => println("a BinOp")
  }


  def main(args: Array[String]): Unit = {
    val expr = Var("ff")
    sealMatch(expr)


  }

}


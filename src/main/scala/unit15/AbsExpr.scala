package unit15

abstract class AbsExpr

case class AbsVar(name: String) extends AbsExpr //样例类:(1)会添加一个跟类同名的工厂方法,不用new就能初始化对象(2)参数列表中的参数都隐式的获得了一个val前缀，会被当做字段处理。
case class AbsNumber(num: Double) extends AbsExpr

//(3)编译器会自然的实现toString，hashCode和equals方法
// (4)编译器还会添加一个copy方法用于制作修改过的拷贝
case class AbsUnOp(operator: String, arg: AbsExpr) extends AbsExpr

case class AbsBinOp(operator: String,
                 left: AbsExpr, right: AbsExpr) extends AbsExpr


object AbsTest {


  //通配模式
  def simplifyTop1(expr: Any): Unit = expr match {
    case AbsBinOp(_, _, _) => println(expr + " is a binary operation") //不关心局部参数，只检查是否同一对象
    case _ => println("default") //通配
  }

  //常量模式
  def describe(x: Any):Unit = x match {
    case 5 => println("five")
    case true => println("truth")
    case AbsBinOp("+", AbsVar("a"), AbsVar("+")) => println("truth match") //定义了确定的参数后，就会做深度的匹配
    //    case AbsBinOp("+",AbsVar("a"),AbsVar("-")) => println("truth match")
    case AbsBinOp(_, _, _) => println(x + " is a binary operation") //如果用占位符表示，则不匹配局部参数
    case _ => println("default")
  }

  //变量模式
  def AbsVariMatch(expr: Any, somethingElse: Int = 88):Unit = {
    expr match {
      case 0 => println("zero")
      case somethingElse => println("not zero")
    }
  }

  //构造方法模式
  def deepMatch(expr: Any):Unit = expr match {
    case AbsBinOp("+", e, AbsNumber(0)) => println("a deep match " + e)
    case _ => println("else mismatch")
  }

  //序列模式
  def seriesMatch(expr: Any):Unit = expr match {
    case List(0, _, _) => println("found it")
    case List(0, _*) => println("found another")
    case _ => println("else")
  }

  //元组模式
  def tupleMatch(expr: Any):Unit = expr match {
    case ("45", c, d) => println("matched " + c + d)
    case (a, b, c) => println("matched " + a + b + c)
    case _ => println("else")
  }

  //带类型的模式
  def typeMatch(expr:Any):Unit = expr match {
    case s:String => println(s.length)
    case m:Map[Int,Int] => println("match inner type")
    case m:Map[_,_] => println(m.size)
    case _ => println(1)
  }

  //变量绑定
  def AbsVarittMatch(expr:Any):Unit=expr match {
    case AbsUnOp("abs",e @ AbsUnOp("abs",_)) => println(e)//e为AbsUnOp("abs",_)实例化后的对象
    case _ => println("else")
  }

  //模式守卫
//  def simplyfyAdd(e:Any) = e match {
//    case AbsBinOp("+",x,x) => AbsBinOp("*",x,AbsNumber(2)) //scala 要求模式是线性的，同一个模式变量只能出现一次
//  }
  implicit def int2AbsVar(x: Int):AbsVar = AbsVar(x.toString)

  def simplifyAdd(e:Any):AbsBinOp = e match {
  case AbsBinOp("+",x,y) if x == y => AbsBinOp("*",x,x)
  case _ => AbsBinOp("*",4,AbsNumber(4))
}
  //模式重叠
  def simplifyAll(expr:AbsExpr):AbsExpr = expr match {
    case AbsUnOp("-",AbsUnOp("-",e)) => simplifyAll(e)
    case AbsBinOp("+",e,AbsNumber(0)) => simplifyAll(e)
    case AbsBinOp("*",e,AbsNumber(1)) => simplifyAll(e)
    case AbsUnOp(op,e) => AbsUnOp(op,simplifyAll(e))
    case _ => expr
  }




  def main(args: Array[String]): Unit = {
    simplifyTop1(AbsBinOp("+", AbsVar("a"), AbsVar("-"))) //AbsBinOp(+,AbsVar(a),AbsVar(-)) is a binary operation
    simplifyTop1("dwdw") //default

    describe(5) //five
    describe(6) //default
    describe(AbsBinOp("+", AbsVar("a"), AbsVar("-"))) //AbsBinOp(+,AbsVar(a),AbsVar(-)) is a binary operation

    AbsVariMatch(88) //not zero

    deepMatch(AbsBinOp("+", AbsVar("wjndjw"), AbsNumber(0))) //a deep match AbsVar(wjndjw)  深度匹配会匹配是否同一类型，且会比较给出的参数的相等性，只要给出的参数相等就行
    deepMatch(AbsBinOp("-", AbsVar("wjndjw"), AbsNumber(0))) //else mismatch
    deepMatch("eee")

    seriesMatch(List(0, 1, 1)) //与构造匹配一致
    seriesMatch(List(1, 0, 0))
    seriesMatch(List(0, 4, 5, 6, 67, 54, 3, 2)) //无视长度

    tupleMatch(("a", 3, 4))
    tupleMatch(("45",45,45))

    typeMatch("34")
    typeMatch(Map[Int,Int](3 -> 5))
    typeMatch(Map[Int,String](3->"wdbewj"))

    AbsVarittMatch(AbsUnOp("abs",AbsUnOp("abs",AbsVar("ff"))))

  }


}




package unit08

object FunctionDemo {

//2.局部函数
  def outer = {
    def inner = {  //这里就是局部函数，好处是里面的函数相当于是私有的。
     println("inner")
    }
    inner
    println("outer")
  }
  //5.一等函数
  val increase:Int=>Int = (x:Int) => x+1 //一等函数

  //6.函数字面量的简写形式
  val someNumber = List(1,2,3,4)
  someNumber.filter( (x:Int) =>x >0) //(x:Int) =>x >0 为字面量
  someNumber.filter(_ < 0) //占位符语法
  //部分应用的函数
  def sum(x:Int,y:Int,z:Int):Int = {x + y + z}
  val sum1 = sum(1,2,_:Int)//一定要写明类型
  //7.闭包
  def makeIncrease(more:Int):Int => Int = (x:Int) => x+more//闭包就是函数可以作为结果返回
 //8.特殊的函数调用形式
  def echo(args:String*):Unit = args.foreach(println)//重复参数
  def speed(distance:Float,time:Float):Float = distance/time //带名字的参数
  def defaultPara(x:Int = 5):Unit = {
    println(x)
  }
  val ff:(String,Int) => Unit = (x:String,y:Int) => println(x+y.toString) //val写法的标准形式:val 变量名:变量类型 = 函数体
  //9.尾递归
  def boom(x:Int):Int =
    if (x == 0) throw new Exception("boom!")
    else boom(x-1)+1   //不是尾递归，因为在递归调用之前还执行了一个递增操作
  def bang(x:Int):Int =
    if (x == 0) throw new Exception("bang!")
    else bang(x-1)
  def isEven(x: Int): Boolean=
    if (x == 0) true else isOdd(x - 1)
  def isOdd(x: Int): Boolean=
    if (x == 0) false else isEven(x - 1)










  def main(args: Array[String]): Unit = {
    outer
    println(increase(9))
    sum1(4)
    val makeIncrease10 = makeIncrease(10)
    println(makeIncrease10(3))
    echo("aaa","bbb","ccc")
    defaultPara()
    ff("sdsaf",343)
//    boom(3)
//    bang(3)
    println(isOdd(22))



  }


}

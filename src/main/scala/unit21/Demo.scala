package unit21

/*
隐式转换
如果 x + y 不能通过编译，那么编译器可能会把他改成convert(x) + y,convert就是某种可用的隐式转换。将x改成某种支持 +方法的对象。
1.必须要标记为implicit，可以是任何变量，函数或者是对象。(标记规则)
2.被插入的隐式转换必须是当前作用域的单个标识，或者跟隐式转换的源类型或目标类型有关联.(作用域规则)
3.每次只能有一个隐式定义被插入。(每次一个规则)
4.只要代码能按编写的样子通过类型检查，就不会尝试使用隐式定义.(显式优先原则)
 */



object Demo {
  /*
  隐式类
   */
  case class Rectangle(width:Int,height:Int)

  //会自动生成下面的隐式转换
//  implicit def RectangleMaker(width:Int) = new RectangleMaker(width)
  //隐式类不能是样例类,并且其构造方法必须有且仅有一个参数,最好是将隐式类作为富包装类来使用。
  implicit class RectangleMaker(width:Int){
    def x(height:Int) = Rectangle(width,height)
  }
 /*
 隐式参数
  */
  class PreferredPrompt(val preference:String)
  class PreferredDrink(val preference:String)

  object Greeter{
    def greet(name:String)(implicit prompt:PreferredPrompt,drink: PreferredDrink) = {
      println("Welcome, "+name+". The system is ready.")
      print("But while you work,")
      println("why not enjoy a cup of "+drink.preference+"?")
      println(prompt.preference)
    }
  }

  //将隐式参数定义在某个对象里，再引入
  object JoesPrefs{
    implicit val prompt = new PreferredPrompt("relax> ")
    implicit val drink = new PreferredDrink("tea")
  }

  def maxListOrdering[T](elements:List[T])(implicit ordering: Ordering[T]):T =
    elements match {
      case List() => throw new IllegalArgumentException("empty list!")
      case List(x) => x
      case x::rest => val maxRest = maxListOrdering(rest)(ordering)
        if (ordering.gt(x,maxRest)) x else maxRest
    }





  def main(args: Array[String]): Unit = {
    implicit def double2Int(x:Double):Int = x.toInt
    val i:Int = 3.5 //加上上面的隐式转换后，可通过编译
    //由隐式类生成的隐式方法提供了Int -> RectangleMaker的转换
    val myRectangle = 3 x 4
    //显式的给出
    val bobsPrompt = new PreferredPrompt("relax> ")
    val teaDrink = new PreferredDrink("tea")
    Greeter.greet("Bob")(bobsPrompt,teaDrink)

    //可以直接在定义域内定义
//    implicit val prompt = new PreferredPrompt("Yes,Master> ")
    //也可以在对面里面定义好后，引入到定义域内
    import JoesPrefs._
    Greeter.greet("Bob")



  }




}

package unit20

/*
20. 抽象成员
 */
object AbstractMenber {

  /*
  20.1 概览
   */
  trait Abstract {
    type absType //抽象类型absType,
    def transform(x: absType): absType //抽象方法
    val initial: absType //val
    var current: absType //var
  }

  class Concrete extends Abstract { //Abstract 特质的具体实现需要填充每个抽象成员的定义
  override type absType = String //相当于给类型String设置了一个别名absType,只要在Concrete类中出现了absType，其含义就是String，所以下面的absType
  def transform(x: String): String = x + x

    val initial: String = "hi"
    var current: String = initial
  }

  /*
  20.2 类型成员
   */
  //类和特质在scala中都不叫抽象类型,scala的抽象类型永远是某个类或特质的成员

  /*
  20.3 抽象的val
  给出val的名称和类型，在具体实现中必须提供。
  使用val 而不是用抽象方法的原因为，val是对象的一个不可变字段，每次通过object.initial调出的值是一样的
   */
  abstract class Fruit {
    val v: String

    def m: String
  }

  abstract class Apple extends Fruit {
    val v: String = "apple"
    val m: String = "apple" // 用val重写def是可以的
  }

  abstract class BadApple extends Fruit {
    //    def v:String="badapple"//错误的，不能用def重写val
    def m: String = "badapple"
  }

  /*
  20.4 抽象的var
  声明为类成员的var默认都带上了getter和setter方法,这一点对于抽象的var同样成立，所以下面注释与未注释部分的意义是等效的。
   */
  trait AbstractTime {
    //    var hour:Int
    //    var minute:Int
    def hour: Int

    //hour的getter方法
    def hour_=(x: Int)

    //hour的setter方法
    def minute: Int

    def minute_=(x: Int)
  }

  /*
  20.5 初始化抽象的val

   */
  trait RationalTrait {
    val numerArg: Int
    val denomArg: Int
    require(denomArg != 0)
    private val g = gcd(numerArg, denomArg)
    val numer:Int = numerArg / g
    val denom:Int = denomArg / g

    private def gcd(a: Int, b: Int): Int =
      if (b == 0) a else gcd(b, a % b)

    override def toString:String = numer +"/"+ denom
  }

  object twoThirds extends {val numerArg = 2;val denomArg = 3} with RationalTrait//预初始化字段
  object Demo{
//    val x = {println("initializing x");"done"} //x 会在demo初始化时就求值，
    lazy val x = {println("initializing x");"done"} //x1只会在真正被用到时被初始化
  }

  /*
  20.6 抽象类型
   */
  class Food
  abstract class Animal{
    type SuitableFood <: Food //表示SuitableFood这个类型有一个上界-Food,意味着Animal子类中任何对SuitableFood的具体实例化都必须是Food的子类。
    def eat(food:SuitableFood)
  }
  class Grass extends Food
  class Cow extends Animal {
    override type SuitableFood = Grass
    override def eat(food: Grass): Unit = {}//如果不使用抽象类型,food的类型为Grass，会编译不通过，因为参数类型不一样.
  }
  /*
  20.7 路径依赖类型
   */
  class DogFood extends Food
  class Dog extends Animal {
    override type SuitableFood = DogFood

    override def eat(food: DogFood): Unit = {}
  }


  def main(args: Array[String]): Unit = {
    val concrete = new Concrete
//    val r = new RationalTrait {
//      override val numerArg: Int = 1
//      override val denomArg: Int = 2
//    } //如果要初始化一个特质，则需要给出抽象成员的具体实现。
    // 实际这里是定义了一个匿名类的实例，这里其实相当于初始化了 new Rational(1,2)
    val x = 2
//    new RationalTrait {
//      override val numerArg: Int = 1*x
//      override val denomArg: Int = 2*x
//    }//这里的定义相当于 new Rational(expr1,expr2),对于类而言，表达式expr1跟2会在类初始化之前被求值，而对于特质而言，情况相反。
    // expr1跟2是在匿名类初始化之后再被求值的，所以在特质初始化时，denomArg的值为默认值0，因此会报错
    /*
    预初始化字段
     */
    new {val numerArg = 1 * x
    val denomArg = 2 * x} with RationalTrait {}//预初始化字段，这样就不会报错.

    Demo // 没有初始化x
    Demo.x//初始化了

    val bessy = new Cow
    val lassie = new Dog
    val bootsie = new Dog
    lassie eat(new bootsie.SuitableFood)



  }

}

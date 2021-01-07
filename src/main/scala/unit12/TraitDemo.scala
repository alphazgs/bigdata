package unit12

/*
12.1 特质的基本特性
 */

trait Philosophical { //跟类一样，有一个默认的超类 AnyRef
  def philosophical():Unit = {
    println("philosophical's philosophical")
  }
  def traitFunc:String //特质中的抽象方法与类一样，需要在混入后实现

}

trait HasLegs

class Animal
class Frog extends Animal with Philosophical with HasLegs {//多个数量混入
  override def toString: String = "Frog" //可以重写超类AnyRef的方法
  override def philosophical(): Unit = println("Frog's philosophical")

  override def traitFunc: String = "Frog"
}






object TraitDemo {

  def main(args: Array[String]): Unit = {
    val frog = new Frog
    frog.philosophical()//philosophical's philosophical
    val phil:Philosophical = frog //特质同样可以当做类型使用,但是不能实例化，只能用于混入
    phil.philosophical()//Frog's philosophical
    val phil1:Philosophical = new Philosophical {
      override def traitFunc: String = "noname Func"}//这里并不是实例化，而是一个实现,可以认为是一个局部实现，只在这个局部有用，最终是实例化了一个类。
    phil1.philosophical()

  }
}

package unit12

/*
可叠加修改的特质
 */
abstract class IntQueue {
  def get: Int

  def put(x: Int)
}


trait Doubling extends IntQueue { // 这样继承后，特质只能混入同样继承自IntQueue的类
  abstract override def put(x: Int): Unit = {
    super.put(2 * x)
  } //必须要加abstract，含义是该特质必须混入某个拥有该方法的具体实现的类中
}

trait Incrementing extends IntQueue {
  abstract override def put(x: Int): Unit = {
    super.put(x + 1)
  }
}

trait Filtering extends IntQueue{
  abstract override def put(x:Int):Unit = {
    if (x >= 0) super.put(x)
  }
}



import scala.collection.mutable.ArrayBuffer

class BasicIntQueue extends IntQueue { //这一级继承不能直接混入特质，但是可以在实例化的时候混入
  private val buf = new ArrayBuffer[Int]

  override def get: Int = buf.remove(0)

  override def put(x: Int): Unit = {
    buf += x
  }
}


class MyQueue extends BasicIntQueue with Doubling with Filtering with Incrementing


object TraitDemo2 {
  def main(args: Array[String]): Unit = {
    val queue = new BasicIntQueue with Incrementing with Filtering with Doubling//实例化的时候混入
    queue.put(1)
    println(queue.get)

    val myqueue = new MyQueue
    myqueue.put(1)//混入的调用效果叠加的，顺序是从右往左
    println(myqueue.get)
  }

}

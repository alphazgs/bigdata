package unit06

import scala.collection.mutable



class ChecksumAccumulator {
  private var sum = 0
  def add(b:Byte):Unit = { //方法参数都是val
    sum += b
  }

  def checksum():Int = {
    ~(sum & 0xFF) +1
  }
}


object ChecksumAccumulator {
  private val cache = mutable.Map.empty[String,Int]
  def calculate(s:String):Int =
    if (cache.contains(s))
      cache(s)
    else {
      val acc = new ChecksumAccumulator
      for (c <- s)
        acc.add(c.toByte)
      val cs = acc.checksum()
      cache += (s -> cs)
      cs
    }



}





object Main {
  def main(args: Array[String]): Unit = {



  }

}
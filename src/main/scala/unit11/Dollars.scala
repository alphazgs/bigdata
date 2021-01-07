package unit11

class Dollars(val amount:Int) extends  AnyVal {
  override def toString: String = "$" + amount
}

/*
1.scala的顶级父类是 Any ，所以可以用==，!=或equals来进行比较，用##或hashCode做哈希，以及toString做格式化.
也同样意味着,在需要的情况下可以重写。
2.Any 有两个子类AnyVal跟AnyRef,AnyVal是scala中所有的值类的父类,Byte,Short,Char,Int,Long,Float,Double,Boolean和Unit,前八个对应的
是java的基本类型.Unit可以对应java的void类型.用来作为那些不返回确定的结果的方法的返回值.Unit有且只有一个实例值,写作()。所有值类是扁平的
都是AnyVal的子类,不同类型之间的转换是通过隐式转换实现的.
3.Any的另一个子类是AnyRef(类似于java中的object类).
4.底类型，底部会有两个类，scala.Null 和 scala.Nothing。用来处理一些极端情况。
 */

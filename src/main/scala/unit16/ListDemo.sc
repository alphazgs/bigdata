//List 是链表，同构，协变的
//同构，列表所有元素必须为同一类型
//协变，对每一组类型S和T，如果S是T的子类型，那么List[S]就是List[T]的子类型
val nums = 1::2::Nil::3::4::Nil
val fruit = List("apple","oranges","pears")
val diag3 = List(
  List(1,0,0),
  List(0,1,0),
  List(0,0,1)
)
val empty = List()
nums.head
nums.tail
nums.isEmpty
//Nil.head//head跟tail必须要不为空，不然报错
4::List(1,2)

val List(a,b,c) = fruit











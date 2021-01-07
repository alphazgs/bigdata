package unit21

object Demo {
  /*
  隐式类
   */
  case class Rectangle(width:Int,height:Int)

  implicit class RectangleMaker(width:Int){
    def x(height:Int) = Rectangle(width,height)
  }
 /*
 隐式参数
  */
  class PreferredPrompt(val preference:String)

  object Greeter{
    def greet(name:String)(implicit prompt:PreferredPrompt) = {
      println("Welcome, "+name+". The system is ready.")
      println(prompt.preference)
    }
  }

  object JoesPrefs{
    implicit val prompt = new PreferredPrompt("Yes,Master> ")
  }



  def main(args: Array[String]): Unit = {
    val myRectangle = 3 x 4
    val bobsPrompt = new PreferredPrompt("relax> ")
    import JoesPrefs._
    Greeter.greet("Bob")
    Map(1 -> "skmw")


  }




}

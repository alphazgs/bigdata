package WC

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._

//批处理WordCount
object WordCount {

  def main(args: Array[String]): Unit = {
    //创建一个批处理的执行环境
    val env:ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    //从文件中读取数据
    val inputPath:String = "D:\\Projects\\flinkdemo\\src\\main\\resources\\hello"
    val inputDataSet:DataSet[String] = env.readTextFile(inputPath)
    //对数据进行转换处理，统计。先分词，再按照word进行分组，最后在进行聚合统计。
    val func= (x:String) => if(x.contains("llo")) (x,"hello",1) else (x,"other",1)

    val resultData = inputDataSet
      .flatMap(_.split(" "))
      .map(func(_))
      .groupBy(0,1)
      .sum(2)
    resultData.print()

//      .groupBy(0,1)
//      .sum(2)
  }
}

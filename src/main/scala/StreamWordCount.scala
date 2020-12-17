import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._


object StreamWordCount {

  def main(args: Array[String]): Unit = {
    //创建流处理执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //设置并行度(线程数)，不设置就会默认当前CPU的线程数，一般不设置，任由flink拉满cpu
//    env.setParallelism(4)
    //从外部命令中提取参数作为启动参数
    val paramTool:ParameterTool = ParameterTool.fromArgs(args)
    val host:String = paramTool.get("host")
    val port:Int = paramTool.getInt("port")

    val inputDataStream:DataStream[String] = env.socketTextStream(host,port)
    val resultDataStream:DataStream[(String,Int)] = inputDataStream
      .flatMap(_.split(" "))
      .filter(_.nonEmpty)
      .map((_,1)).setParallelism(8) //由于每一步计算都是分离的，所以在每一步计算都可以设置并行度。
      .keyBy(0)//基于某个key进行分组时，会对数据进行重分区，类似于spark的shuffle。重分区的依据是key的HashCode(取余，取模等等操作)
      .sum(1).setParallelism(16)

    resultDataStream.print().setParallelism(1)

    //启动任务执行
    env.execute("WordCount")



  }

}

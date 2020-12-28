package StreamApi

import java.util.{Properties, Random}

import org.apache.calcite.schema.AggregateFunction
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011

case class SensorReading(id: String, timestamp: Long, temperature: Double)//定义样例类

class MySensorSource extends SourceFunction[SensorReading]{
  var running:Boolean =true

  override def cancel(): Unit = {
    running = false
  }

  override def run(ctx: SourceFunction.SourceContext[SensorReading]): Unit = {
    val rand = new Random()
    var curTemp = 1.to(10).map(i => ("sensor_"+i,65+rand.nextGaussian()*20))
    while(running){
      curTemp = curTemp.map(t => (t._1,t._2+rand.nextGaussian()))
      val curTime = System.currentTimeMillis()
      curTemp.foreach(t => ctx.collect(SensorReading(t._1,curTime,t._2)))
    }
    Thread.sleep(100)
  }
}


object SourceDemo {

  def main(args: Array[String]): Unit = {
//    /*
//    1. 从集合读取数据集
//     */
    val env = StreamExecutionEnvironment.getExecutionEnvironment//创建执行环境
//    val stream1 = env
//      .fromCollection(List(
//        SensorReading("sensor_1", 154771899, 35.8),
//        SensorReading("sensor_2", 154771898, 35.5),
//        SensorReading("sensor_3", 154771897, 35.4)
//      ))
////    env.fromElements(1.0,35,"hello")//更简单做测试
//    val ss:DataStream[Char] = stream1.map(_.id.charAt(1))
//    ss.print().setParallelism(1)
//    env.execute()
//    /*
//    2. 从文件中读取数据
//     */
//    val inputpath = "D:\\Projects\\flinkdemo\\src\\main\\resources\\sensor.txt"
//    val stream2 = env.readTextFile(inputpath)
//    stream2.print().setParallelism(1)
//    env.execute()
    /*
    3. 从kafka读取数据
     */
//    val properties = new Properties()
//    properties.setProperty("bootstrap.servers","192.168.132.130:9092")
//    properties.setProperty("group.id","consumer-group")
//    val stream3 = env.addSource(new FlinkKafkaConsumer011[String]("sensor",new SimpleStringSchema(),properties))
//    stream3.print()
//    env.execute()

    /*
    4.自定义Source
     */
    val mySource = new MySensorSource
    val stream4:DataStream[SensorReading] = env.addSource(mySource)
    stream4.keyBy("id")
      .timeWindow(Time.seconds(5))
      .sum("temperature").print()
    env.execute()









  }
}

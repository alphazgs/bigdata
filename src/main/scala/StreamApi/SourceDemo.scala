package StreamApi

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

case class SensorReading(id: String, timestamp: Long, temperature: Double)

object SourceDemo {

  def main(args: Array[String]): Unit = {
    /*
    1.从集合读取数据集
     */
    val streamEnv = StreamExecutionEnvironment.getExecutionEnvironment
    val stream1 = streamEnv
      .fromCollection(List(
        SensorReading("sensor_1", 154771899, 35.8),
        SensorReading("sensor_1", 154771899, 35.8),
        SensorReading("sensor_1", 154771899, 35.8)
      ))
    stream1.print("stream1:").setParallelism(1)
    streamEnv.execute()




  }
}

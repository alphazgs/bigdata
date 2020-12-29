package StreamApi

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala._
import org.apache.log4j.{Level, Logger}


object TransformDemo {
  Logger.getLogger("org").setLevel(Level.ERROR)



  def main(args: Array[String]): Unit = {
    val env  = StreamExecutionEnvironment.getExecutionEnvironment
    val inputPath = "D:\\Projects\\flinkdemo\\src\\main\\resources\\sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    //先转换成样例类类型
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0),arr(1).trim.toLong,arr(2).trim.toDouble)
    })
    //分组聚合，输出每个传感器当前最小值
    val aggStream = dataStream
      .keyBy("id")
      .minBy("temperature")

    //需要输出当前最小的温度值，以及最近的时间戳，用reduce
    val resultStream = dataStream
      .keyBy("id")
      .reduce((curData,newData)=>SensorReading(curData.id,newData.timestamp,curData.temperature.min(newData.temperature)))

//    val resultStream1 =


    resultStream.print()
    env.execute()
  }

}


class myReduceFunction extends ReduceFunction[SensorReading]{
  override def reduce(t: SensorReading, t1: SensorReading): SensorReading = SensorReading(t.id,t1.timestamp,t.temperature.min(t1.temperature))
}

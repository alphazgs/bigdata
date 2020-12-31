package StreamApi

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala._
import org.apache.log4j.{Level, Logger}


object TransformDemo {
  Logger.getLogger("org").setLevel(Level.ERROR)


  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val inputPath = "D:\\Projects\\flinkdemo\\src\\main\\resources\\sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    //先转换成样例类类型
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).trim.toLong, arr(2).trim.toDouble)
    })
    /*
    5. 滚动聚合算子（sum,min,max,minBy,maxBy）
     */

    //分组聚合，输出每个传感器当前最小值
    val aggStream = dataStream
      .keyBy("id")
      .minBy("temperature")

    //需要输出当前最小的温度值，以及最近的时间戳，用reduce
    val resultStream = dataStream
      .keyBy("id")
      .reduce((curData, newData) => SensorReading(curData.id, newData.timestamp, curData.temperature.min(newData.temperature)))

    val resultStream1 = dataStream.keyBy("id")
      .reduce(new myReduceFunction)
    /*
    分流
    split和select
     */
    //split 根据某些特征把一个DataStream转换成两个或多个DataStream
    val splitStream = dataStream.split(sensorData => {
      if (sensorData.temperature > 35.7) List("high", "gg") else List("low") //TraversableOnce(仅可遍历一次的集合)是所有集合类的顶级父类，所以返回只要是String的集合就行。
    }) //而且集合里面包含的String都是这个子DataStream的别名

    val high = splitStream.select("gg") //或者high = splitStream.select("high")
    val low: DataStream[SensorReading] = splitStream.select("low")
    val all = splitStream.select("gg", "low")
    //    high.print("high")
    //    low.print("low")
    //    all.print("all")

    /*
    合流
    connect 和 coMap  只能操作两个
     */
    val warning: DataStream[(String, Double)] = high.map(data => (data.id, data.temperature))
    val connected = warning.connect(low)
    val coMap = connected.map(
      warningData => (warningData._1, warningData._2, "warning"),
      lowData => (lowData.id, "healthy")
    ) //合成了一个流

    /*
    联合
    union  必须要两个相同的数据类型
     */
    val unionStream = high.union(low)






    coMap.print()
    env.execute()
  }

}

//RedeceFunction类，需要实现一个reduce方法。
class myReduceFunction extends ReduceFunction[SensorReading] {
  override def reduce(t: SensorReading, t1: SensorReading): SensorReading = SensorReading(t.id, t1.timestamp, t.temperature.min(t1.temperature))
}

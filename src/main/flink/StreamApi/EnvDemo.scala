package StreamApi

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object EnvDemo {

  object EnvironmentDemo{
    /*
    1. Environment
    创建执行环境，表示当前执行程序的上下文。
    getExecutionEnvironment会根据查询运行的方式决定返回什么样的运行环境。
    */
    val batchEnv:ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment//批计算
    val streamEnv:StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment//流计算
    val localEnv:StreamExecutionEnvironment = StreamExecutionEnvironment.createLocalEnvironment(1)//返回本地执行环境，需要指定默认的并行度
    val remoteEnv:ExecutionEnvironment = ExecutionEnvironment.createRemoteEnvironment("jobmanage-hostname",6123,"yourpath//wordcount.jar")
  }

}

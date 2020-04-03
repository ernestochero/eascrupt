package commons

import commons.EaScruptEnv.ScruptEnv
import zio.{ZEnv, ZLayer}
import modules.kafkaProducerModule._
import modules.scrapModule._

object EaScruptEnv {
  type ScruptEnvType = KafkaProducerType with ScrapModuleType
  object ScruptEnv {
    val any: ZLayer[ScruptEnvType, Nothing, ScruptEnvType] =
      ZLayer.requires[ScruptEnvType]
    val live: ZLayer[Any, Nothing, KafkaProducerType with ScrapModuleType] =
      KafkaProducerModule.live ++ ScrapModule.live
  }
}
object Helper {
  type EnvironmentType = zio.ZEnv with EaScruptEnv.ScruptEnvType
  val liveEnvironments: ZLayer[Any, Nothing, EnvironmentType] =
    ZEnv.live ++ ScruptEnv.live
}

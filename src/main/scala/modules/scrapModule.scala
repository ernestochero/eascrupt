package modules
import scrap._
import zio.{ Has, ZIO, ZLayer }
package object scrapModule {
  type ScrapModuleType = Has[ScrapModule.Service]
  object ScrapModule {
    trait Service {
      def someInformation[T](scrap: Scrap[T]): ZIO[ScrapModuleType, Nothing, Map[String, String]]
    }
    val live: ZLayer.NoDeps[Nothing, ScrapModuleType] = {
      ZLayer.succeed {
        new Service {
          override def someInformation[T](
            scrap: Scrap[T]
          ): ZIO[ScrapModuleType, Nothing, Map[String, String]] =
            ZIO.succeed(
              scrap.getSomething
            )
        }
      }
    }
  }
  def someInformation[T](scrap: Scrap[T]): ZIO[ScrapModuleType, Nothing, Map[String, String]] =
    ZIO.accessM[ScrapModuleType](_.get.someInformation(scrap))
}

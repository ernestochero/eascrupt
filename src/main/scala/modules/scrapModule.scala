package modules
import scrap._
import zio.{Has, ZIO, ZLayer}
package object scrapModule {
  type ScrapModuleType = Has[ScrapModule.Service]
  object ScrapModule {
    trait Service {
      def metaContentAttr(implicit scrap: Scrap): ZIO[ScrapModuleType, Nothing, String]
    }
    val live: ZLayer.NoDeps[Nothing, ScrapModuleType] = {
      ZLayer.succeed {
        new Service {
          override def metaContentAttr(implicit scrap: Scrap): ZIO[ScrapModuleType, Nothing, String] =
            ZIO.succeed(
              scrap.getMetaContentAttr
            )
        }
      }
    }
  }
  def bodyAttr(implicit  scrap: Scrap): ZIO[ScrapModuleType, Nothing, String] = {
    ZIO.accessM[ScrapModuleType](_.get.metaContentAttr)
  }
}

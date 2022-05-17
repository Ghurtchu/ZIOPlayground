package handling_errors

import zio.*
import CatchingAllErrors.openFile

object Folding {

  // Scala's Option and Either data types have fold which let you handle both failure and success
  // at the same time. In a similar fashion, ZIO effects also have several methods that allow you to
  // handle both failure and success

  // fold lets you non-effectfully handle both failure and success, by supplyting a non-effectful handler for each case

  lazy val DefaultData: Array[Byte] = Array(0, 0)

  val primaryOrDefaultData: UIO[Array[Byte]] =
    openFile("primary.json").fold(
      _ => DefaultData,
      identity)

  // foldM lets you efectfully handle both failure and success, by supplying an effectful (but still pure) handler for each case:

  val primaryOrSecondaryData: IO[java.io.IOException, Array[Byte]] =
    openFile("primary.data").foldM(
      _ => openFile("secondary.data"),
      data => ZIO.succeed(data)
    )

  sealed trait Content

  case class ValueContent(urls: List[String]) extends Content
  case class NoContent(message: String) extends Content

  def readUrls(path: String): IO[java.io.IOException, Content] =
    ZIO.effectTotal(ValueContent(List("url1", "url2", "url3")))

  val urls: UIO[Content] = readUrls("config.json").foldM(
    error => IO.succeed(NoContent(error.getMessage)),
    success => fetchContent(success)
  )
  
  def fetchContent(content: Content): UIO[Content] = ZIO.succeed(content)



}

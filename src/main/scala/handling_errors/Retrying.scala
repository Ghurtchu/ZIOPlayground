package handling_errors

import zio.clock._
import zio.*
import CatchingAllErrors.openFile

class Retrying {

  // retrying failed effects

  // ZIO.retry will return a new effect that will retry the first effect if it fails
  // according to the specified policy

  val retriedOpenFile: ZIO[Clock, java.io.IOException, Array[Byte]] =
    openFile("primary.json").retry(Schedule.recurs(5))

  // next is ZIO.retryOrElse which allows specification of a fallback to use
  // if the effect does not succeed with the specified policy:

  lazy val DefaultData: Array[Byte] = Array(0, 0)

  openFile("primary.json").retryOrElse(
    Schedule.recurs(5),
    (_, _) => ZIO.succeed(DefaultData)
  )


}

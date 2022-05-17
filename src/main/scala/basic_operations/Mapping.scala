package basic_operations

import zio._

object Mapping {

  // The example of mapping over the success channel
  // map value
  val succeeded: ZIO[Any, Nothing, Unit] = IO.succeed(21).map(_ * 2)
  
  // The example of mapping over the failure message
  // map error
  val failed: IO[Exception, Unit] = IO.fail("Oh no!").mapError(msg => new Exception(msg))
  
  // Note that mapping over an effect's success or error channel does not change the success or
  // failure of the effect. in the same way that mapping over an Either does not change whether the
  // Either is Left or Right.
}

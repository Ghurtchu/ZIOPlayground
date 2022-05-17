package creating_effects

import zio.*

object FromFailureValues {

  val failure: IO[String, Nothing] = ZIO.fail("Oh no!")

  val failure2 = Task.fail(new Exception("Boom"))


}

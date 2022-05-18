package testing_effects

import zio.*

object ProvidingEnvironments {

  // Effects that require an environment cannot be run without first providing their environment to them.
  // The simplest way to provide an effect the environment that it requires is to use the ZIO#provide method:

  val square: URIO[Int, Int] = for {
    env <- ZIO.environment[Int]
  } yield env * env

  val result: UIO[Int] = square provide 42

  // The combination of ZIO.accessM and ZIO#provide are all that is necessary to fully use environmental effects for easy testability.

}

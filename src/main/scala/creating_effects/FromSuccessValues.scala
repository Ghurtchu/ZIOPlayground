package creating_effects
import zio.console.Console
import zio.{ExitCode, Task, UIO, URIO, ZIO}

object FromSuccessValues {

  // success
  val meaningOfLife: UIO[Int] = ZIO.succeed {
    println("calculating...")
    Thread sleep 1000
    42
  }

  // success
  val task: UIO[Double] = Task.succeed {
    println("inside task")
    Thread sleep 1000
    Math.random()
  }

  // side effecting
  val now: UIO[Long] = ZIO.effectTotal(System.currentTimeMillis)

}

package creating_effects

import zio.{Task, ZIO, UIO}

object FromSideEffects {

  object SynchronousSideEffects {
    import scala.io.StdIn

    // may throw exception
    val getStrLn: Task[String] = ZIO.effect(StdIn.readLine())

    // won't throw exception
    def putLine(line: String): UIO[Unit] = ZIO.effectTotal(println(line))
  }

}

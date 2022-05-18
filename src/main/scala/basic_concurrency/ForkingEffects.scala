package basic_concurrency

import zio._

object ForkingEffects {

  // the following code creates a single fiber, which executes fib(100):

  def fib(n: Long): UIO[Long] = UIO {
    if n <= 1 then UIO.succeed(n)
    else fib(n - 1).zipWith(fib(n - 2))(_ + _)
  }.flatten

  val fib100Fiber: UIO[Fiber[Nothing, Long]] = for {
    fiber <- fib(100).fork
  } yield fiber

}

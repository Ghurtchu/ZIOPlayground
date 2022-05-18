package basic_concurrency

object InterruptingFibers {

  // A fiber whose result is no longer needed may be interrupted which immediately terminates the fiber,
  // safely releasing all resources and running all finalizers

  for {
    fiber <- zio.IO.succeed("Hi!").forever.fork
    exit <- fiber.interrupt
  } yield exit

  // By design the effect returned by interrupt does not resume until the fiber has completed.
  // If this behaviour is not desired you can fork the interruption itself

  for {
    fiber <- zio.IO.succeed("Hi!").forever.fork
    _     <- fiber.interrupt.fork
  } yield()


}

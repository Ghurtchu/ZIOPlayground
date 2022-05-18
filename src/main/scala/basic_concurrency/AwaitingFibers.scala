package basic_concurrency

object AwaitingFibers {

  // Another method on Fiber is Fiber#await which returns an effect containing an Exit value,
  // which provides full information on how the fiber completed.

  for {
    fiber <- zio.IO.succeed("Hi!").fork
    exit <- fiber.await
  } yield exit

}

package basic_concurrency

import zio._

object JoiningFibers {

  // one of the methods on Fiber is Fiber#join, which returns an effect. The effect returned by Fiber#join
  // will succeed or fail as per the fiber:

  for {
    fiber <- IO.succeed("Hi").fork
    message <- fiber.join
  } yield message

}

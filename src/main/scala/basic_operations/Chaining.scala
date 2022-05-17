package basic_operations

import zio._
import zio.console._

import java.io.IOException

object Chaining {

  type CIO[+A] = ZIO[Console, IOException, A]


  // If the first effect fails, the callback passed to `flatMap` will never be invoked and the 
  // composed effect returned by `flatMap` will also fail.
  
  val sequenced: CIO[Unit] =
    getStrLn.flatMap(input => putStrLn(s"You entered $input"))
  
  val alternativeSequenced: CIO[Unit] = for {
    input <- getStrLn
    _ <- putStrLn(s"You entered $input")
  } yield ()
  
  // in any chain of effects the first failure will short-circuit the whole chain, just like
  // throwing an exception will prematurely exit a sequence of statements
}

package intro

import zio.*

object Finalizing {

  // similar functionality to try/finally with ZIO#ensuring method.

  // like try/finally, the `ensuring` operation guarantees that if an effect begins executing
  // and then terminates (for whatever reason) then the finalizer will begin executing.

  val finalizer: UIO[Unit] = UIO.effectTotal(println("finalizing"))

  val finalized: IO[String, Unit] = IO.fail("Failed").ensuring(finalizer)

  // the finalizer is not allowed to fail, which menas that it must handle any errors internally

  // like try/finally, finalizers can be nested and the failure of any inner finalizer will not affect
  // outer finalizers. Nested finalizers will be executed in reverse order and linearly (not in parallel)

  // Unlike try/finally, ensuring works across all types of effects, including async and concurrent

}

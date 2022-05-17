package basic_operations

import zio.*
import zio.console._


object Zipping {

  // We can combine two effects into a single effect with zip method.
  // the resulting effect succeeds with a tuple that contains the success values of both effects:
  val zipped: UIO[(String, Int)] = ZIO.succeed("4").zip(ZIO.succeed(2))
  // zip operates sequentially, the effect on the left side is executed before the effect on the right side.
  // if any of the zipped operation fails then the whole zip fails

  // sometimes when the success value of one part is not useful such as Unit
  // it might be more convenient to user zipLeft or zipRight

  // They first perform `zip` and then map over the tuple to discard one side or the other:

  // zip right because we need (Unit, String) as a return type
  val zipRight = putStrLn("What is your name?").zipRight(getStrLn)

  // more conventional operator
  val zipRight2 = putStrLn("What is your name?") *> getStrLn

}

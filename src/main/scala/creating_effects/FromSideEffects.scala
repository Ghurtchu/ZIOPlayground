package creating_effects

import zio.{Task, ZIO, UIO, IO}

object FromSideEffects {

  object SynchronousSideEffects {
    import scala.io.StdIn

    // may throw exception
    val getStrLn: Task[String] = ZIO.effect(StdIn.readLine())

    // won't throw exception
    def putLine(line: String): UIO[Unit] = ZIO.effectTotal(println(line))
  }

  object AsynchronousSideEffects {

    case class User(id: String, name: String)

    class AuthError(val message: String) extends RuntimeException

    object legacy {
      def login(onSuccess: User => Unit, onFailure: AuthError => Unit): Unit = ???
    }

    val login: IO[AuthError, User] = IO.effectAsync[AuthError, User] { callBack =>
      legacy.login(
        user => callBack(IO.succeed(user)),
        err => callBack(IO.fail(err))
      )
    }

  }

}

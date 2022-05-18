package testing_effects

object Environments {

  // simple and the most ergonomic way of testing effects is called "environmental effects"

  // ZIO data type has an `R` type parameter which is used to describe the type of environment required by the effect.

  // ZIO effects can access the environment using ZIO.environment which provides direct access to the environment as a value of type R

  for {
    env <- zio.ZIO.environment[Int]
    _   <- zio.console.putStrLn(s"The value of the environment is $env")
  } yield env

  // the env does not have to be the primitive value, it can be much more complex, like a trait or case class.

  // When the env is a type with fields, then ZIO.access method can be used to access a given part of the environment in a single method call

  final case class Config(server: String, port: Int)

  import zio.*

  val configString: URIO[Config, String] = for {
    server <- ZIO.access[Config](_.server)
    port   <- ZIO.access[Config](_.port)
  } yield s"Server: $server, port: $port"

  // Even effects themselves can be stored in the environment! in this case to access and execute an effect
  // the ZIO.accessM method can be used

  trait DatabaseOps {
    def getTableNames: Task[List[String]]
    def getColumnNames(table: String): Task[List[String]]
  }

  val tablesAndColumns: ZIO[DatabaseOps, Throwable, (List[String], List[String])] =
    for {
      tables  <- ZIO.accessM[DatabaseOps](_.getTableNames)
      columns <- ZIO.accessM[DatabaseOps](_.getColumnNames("user_table"))
    } yield (tables, columns)

  // When an effect is accessed from the environment, as in the preceding example, the effect is called
  // an `environmental effect`.

}

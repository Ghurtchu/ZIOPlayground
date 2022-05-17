package handling_errors

import zio.{IO, ZIO}

import java.io.{FileNotFoundException, IOException}
import java.nio.file.{Files, Paths}

object CatchingSomeErrors {

  val data: IO[IOException, Array[Byte]] = openFile("primary.data").catchSome {
    case _: FileNotFoundException => openFile("backup.data")
  }

  def openFile(path: String): IO[IOException, Array[Byte]] =
    ZIO.effectTotal(Files.readAllBytes(Paths.get(path)))

}

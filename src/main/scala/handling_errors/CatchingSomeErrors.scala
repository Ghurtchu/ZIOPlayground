package handling_errors

import zio.{IO, ZIO}

import java.io.{FileNotFoundException, IOException}
import java.nio.file.{Files, Paths}
import CatchingAllErrors.openFile

object CatchingSomeErrors {

  val data: IO[IOException, Array[Byte]] = openFile("primary.data").catchSome {
    case _: FileNotFoundException => openFile("backup.data")
  }

}

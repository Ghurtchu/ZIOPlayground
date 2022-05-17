package handling_errors

import zio.*
import java.io.IOException
import CatchingAllErrors.openFile


object FallBack {

  // We can try one effect or if it fails we can try another with orElse combinator:

  val primaryOrBackupData: IO[IOException, Array[Byte]] = openFile("primaryData.json")
    .orElse(openFile("backupData.json"))

}

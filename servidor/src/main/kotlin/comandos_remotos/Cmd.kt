package comandos_remotos

import java.io.BufferedReader
import java.io.InputStreamReader

object Cmd {


    fun runWithOutput(input: String): String {


        val mProcessBuilder = ProcessBuilder(input).redirectErrorStream(true).start()
        val mBufferedReader = BufferedReader(InputStreamReader(mProcessBuilder.inputStream))
        val commandOutput = StringBuilder()

        var line: String?
        while (mBufferedReader.readLine().also { line = it } != null) {
            commandOutput.append(line)
        }

        return commandOutput.toString()
    }

    fun run(input: String) {
        Runtime.getRuntime().exec(input);
    }

    fun runVbsScript(path: String) {
        Runtime.getRuntime().exec(arrayOf("wscript.exe", path))
    }


}
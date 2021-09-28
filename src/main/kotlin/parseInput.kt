import java.io.File

enum class QueryInput {
    Help, Links, Error
}

data class Input(val type: QueryInput = QueryInput.Error,
                 val format: PrintFormat = PrintFormat.Unified,
                 val link1: File = File(""),
                 val link2: File = File(""))

fun parseInput(input: List<String?>): Input {
    return when (input.size) {
        1 -> parseHelp(input[0])
        2 -> parseLinks(input[0], input[1])
        3 -> parseLinksFormat(input[0], input[1], input[2])
        else -> wrongInput()
    }
}

fun wrongInput(): Input {
    return Input()
}

fun inputFormat(format: String?): PrintFormat? {
    return when (format) {
        "d" -> PrintFormat.Default
        "u" -> PrintFormat.Unified
        else -> null
    }
}

fun parseLinksFormat(link1: String?, link2: String?, format: String?): Input {
    val printFormat = inputFormat(format)
    if (link1 != null && link2 != null && printFormat != null) {
        val file1 = File(link1)
        val file2 = File(link2)
        if (!file1.exists() || !file2.exists()) {
            return wrongInput()
        }
        return Input(QueryInput.Links, printFormat, file1, file2)
    } else {
        return wrongInput()
    }
}

fun parseLinks(link1: String?, link2: String?): Input {
    return parseLinksFormat(link1, link2, "u")
}

fun parseHelp(input: String?): Input {
    return when (input) {
        "help" -> Input(QueryInput.Help)
        else -> wrongInput()
    }
}

fun getInput(): List<String?> {
    println("Enter links to the two texts you want to compare:\nPrint 'help' for examples")
    val input: MutableList<String?> = mutableListOf()
    for (i in 1..3) {
        input.add(readLine())
    }
    while (input.isNotEmpty() && input[input.lastIndex].isNullOrEmpty()) {
        input.removeLast()
    }
    return input
}

fun parseInput(args: Array<String>): Input {
    return parseInput(when {
        args.isEmpty() -> getInput()
        else -> args.toList()
    })
}
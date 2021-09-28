import java.io.File

enum class InputType {
    Help, Links, Error
}

data class Input(val type: InputType = InputType.Error,
                 val format: PrintFormat = PrintFormat.Unified,
                 val link1: String = "",
                 val link2: String = "")

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
        if (!File(link1).exists() || !File(link2).exists()) {
            return wrongInput()
        }
        return Input(InputType.Links, printFormat, link1, link2)
    } else {
        return wrongInput()
    }
}

fun parseLinks(link1: String?, link2: String?): Input {
    return parseLinksFormat(link1, link2, "u")
}

fun parseHelp(input: String?): Input {
    return when (input) {
        "help" -> Input(InputType.Help)
        else -> wrongInput()
    }
}

fun getInput(): List<String?> {
    println("Введите ссылки на два текста, посмотреть примеры введите h")
    val input: MutableList<String?> = mutableListOf()
    for (i in 1..3) {
        input.add(readLine())
    }
    return input
}

fun parseInput(args: Array<String>): Input {
    return parseInput(when {
        args.isEmpty() -> getInput()
        else -> args.toList()
    })
}
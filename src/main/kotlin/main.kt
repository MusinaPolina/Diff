import java.io.File

fun printDiff(file1: File, file2: File, format: PrintFormat) {
    val text1 = Text(file1)
    val text2 = Text(file2)
    val diff = Diff(text1, text2)
    print(when (format) {
        PrintFormat.Unified -> diff.printUnified()
        PrintFormat.Default -> diff.printDefault()
    })
}

fun printHelp() {
    val text1 = Text(File("src/main/kotlin/text1.txt"))
    val text2 = Text(File("src/main/kotlin/text2.txt"))
    val diff = Diff(text1, text2)
    println("Default output:")
    println(diff.printDefault())
    println("Unified output:")
    println(diff.printUnified())
}

fun printError() {
    println("Something went wrong")
}

fun printInput(input: Input) {
    when (input.type) {
        QueryInput.Links -> printDiff(input.link1, input.link2, input.format)
        QueryInput.Help -> printHelp()
        else -> printError()
    }
}

fun main(args: Array<String>) {
    val input = parseInput(args)
    printInput(input)
}

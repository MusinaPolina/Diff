import java.io.File

fun printLinks(file1: File, file2: File, format: PrintFormat) {
    val text1 = file1.readLines()
    val text2 = file2.readLines()
    print(printDiff(text1, text2, format))
}

fun printHelp() {
    val file1 = File("src/main/kotlin/text1.txt")
    val file2 = File("src/main/kotlin/text2.txt")
    println("Default output:")
    printLinks(file1, file2, PrintFormat.Default)
    println("Unified output:")
    printLinks(file1, file2, PrintFormat.Unified)
}

fun printError() {
    println("Something went wrong")
}

fun printInput(input: Input) {
    when (input.type) {
        QueryInput.Links -> printLinks(input.link1, input.link2, input.format)
        QueryInput.Help -> printHelp()
        else -> printError()
    }
}

fun main(args: Array<String>) {
    val input = parseInput(args)
    printInput(input)
}

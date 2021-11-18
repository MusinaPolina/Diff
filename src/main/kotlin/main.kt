import java.io.File

/* Prints diff in given format, read files by given links*/

fun printByLinks(file1: File, file2: File, format: PrintFormat) {
    val text1 = file1.readLines()
    val text2 = file2.readLines()
    print(countDiff(text1, text2, format))
}

/* Prints an example of diff formats*/

fun printHelp() {
    val file1 = File("src/main/kotlin/text1.txt")
    val file2 = File("src/main/kotlin/text2.txt")
    println("Original output, print 'r' after links:")
    printByLinks(file1, file2, PrintFormat.Original)
    println("Unified output, print 'u' after links:")
    printByLinks(file1, file2, PrintFormat.Unified)
}

fun printError() {
    println("Something went wrong")
}

fun printInput(input: Input) {
    when (input.type) {
        QueryInput.Links -> printByLinks(input.link1, input.link2, input.format)
        QueryInput.Help -> printHelp()
        else -> printError()
    }
}

fun main(args: Array<String>) {
    val input = parseInput(args)
    printInput(input)
}

enum class LineType(val value: Char){
    Add('a'), Delete('d'), Common('c')
}

enum class PrintFormat {
    Default, Unified
}

/*  Category of line in text: it has been added, deleted or remained the same
    and its index in both texts (-1 in case it's absent) */

data class DiffLine(var type: LineType, var firstIndex: Int = -1, var secondIndex: Int = -1)

/* Values and previous position of state of dp to count the longest common subsequence */

typealias Texts = Pair<List<String>, List<String>>

fun printDiff(text1: List<String>, text2: List<String>, format: PrintFormat): String {
    val diff = buildDiffText(text1, text2, countLongestCommonSubsequence(text1, text2))
    val texts = Pair(text1, text2)
    return (when (format) {
        PrintFormat.Unified -> printUnified(diff, texts)
        PrintFormat.Default -> printDefault(diff, texts)
    }).toString()
}
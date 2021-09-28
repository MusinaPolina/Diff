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

class Diff(private val text1: List<String>, private val text2: List<String>) {
    var dText: List<DiffLine> = listOf()
    val texts = Pair(text1, text2)

    init {
        longestCommonSubsequence()
    }

    private fun longestCommonSubsequence() {
        dText = buildDiffText(text1, text2, countLongestCommonSubsequence(text1, text2))
    }

    fun printDefault(): String {
        return printDefault(this).toString()
    }

    fun printUnified(border: Int = 3): String {
        return printUnified(this, border)
    }
}
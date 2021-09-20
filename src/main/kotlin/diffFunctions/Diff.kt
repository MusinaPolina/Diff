enum class LineType(val value: Char){
    Add('a'), Delete('d'), Common('c')
}

/*  Category of line in text: it has been added, deleted or remained the same
    and its index in both texts (-1 in case it's absent) */

data class DiffLine(var type: LineType, var firstIndex: Int = -1, var secondIndex: Int = -1)

/* Values and previous position of state of dp to count the longest common subsequence */

class Diff(text1: Text, text2: Text) {
    var dText: List<DiffLine> = listOf()
    val texts = Pair(text1, text2)

    init {
        longestCommonSubsequence()
    }

    private fun longestCommonSubsequence() {
        dText = buildDiffText(texts.first, texts.second, countLongestCommonSubsequence(texts.first, texts.second))
    }

    fun printDefault(): String {
        return printDefault(this).toString()
    }
}
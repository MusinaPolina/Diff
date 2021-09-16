enum class LineType(val value: Char){
    Add('a'), Delete('d'), Common('c')
}

/*  Category of line in text: it has been added, deleted or remained the same
    and its index in both texts (-1 in case it's absent) */

data class DiffLine(val type: LineType, val firstIndex: Int = -1, val secondIndex: Int = -1)


/* Values and previous position of state of dp to count the longest common subsequence */

data class DPValue(val value: Int = 0, val previous: Pair<Int, Int> = Pair(0, 0))

/* Function calculates an array of dp for LongestCommonSubsequence*/

fun countLongestCommonSubsequence(firstText: Text, secondText: Text): Array<Array<DPValue>> {
    val firstSize = firstText.text.size
    val secondSize = secondText.text.size
    val dp: Array<Array<DPValue>> = Array(firstSize + 1) { Array(secondSize + 1) { DPValue() } }

    val firstRange = 1..firstSize
    val secondRange = 1..secondSize

    for (i in firstRange) {
        for (j in secondRange) {
            if (firstText.text[i - 1] == secondText.text[j - 1]) {
                dp[i][j] = DPValue(dp[i - 1][j - 1].value + 1, Pair(i - 1, j - 1))
            } else {
                dp[i][j] = if (dp[i - 1][j].value > dp[i][j - 1].value) {
                    DPValue(dp[i - 1][j].value, Pair(i - 1, j))
                } else {
                    DPValue(dp[i][j - 1].value, Pair(i, j - 1))
                }
            }
        }
    }
    return dp
}

/* Function adds changed (added and deleted) lines to list*/

fun addLines(dText: MutableList<DiffLine>, from: Int, to: Int, type: LineType) {
    for (i in from downTo to) {
        dText.add(DiffLine(type, i, i))
    }
}

fun buildDiffText(firstText: Text, secondText: Text, dp: Array<Array<DPValue>>): MutableList<DiffLine> {
    val dText: MutableList<DiffLine> = mutableListOf()
    var indexes = Pair(firstText.text.size, secondText.text.size)
    do {
        val previous = dp[indexes.first][indexes.second].previous
        var (first, second) = indexes
        first -= 1
        second -= 1
        if (first != -1 && second != -1 && firstText.text[first] == secondText.text[second]) {
            dText.add(DiffLine(LineType.Common, first, second))
        } else {
            addLines(dText, second, previous.second, LineType.Add)
            addLines(dText, first, previous.first, LineType.Delete)
        }
        indexes = previous
    } while (indexes != Pair(0, 0)) //(0, 0) base of dp
    dText.reverse()
    return dText
}

class Diff {
    val dText: MutableList<DiffLine> = mutableListOf()
    val firstText = Text()
    val secondText = Text()

    fun longestCommonSubsequence() {
        //val dp = countLongestCommonSubsequence(firstText, secondText)
    }
}
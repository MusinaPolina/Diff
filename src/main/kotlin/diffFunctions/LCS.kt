/* Contains the data for dynamic programming */

data class DPValue(val value: Int = 0, val previous: Pair<Int, Int> = Pair(0, 0))

/* Function calculates an array of dp for LongestCommonSubsequence*/

fun countLongestCommonSubsequence(firstText: Text, secondText: Text): List<List<DPValue>> {
    val firstSize = firstText.text.size
    val secondSize = secondText.text.size
    val dp: List<MutableList<DPValue>> = List(firstSize + 1) { MutableList(secondSize + 1) { DPValue() } }
    val firstRange = 1..firstSize
    val secondRange = 1..secondSize

    for (first in firstRange) {
        for (second in secondRange) {
            if (firstText.text[first - 1] == secondText.text[second - 1]) {
                dp[first][second] = DPValue(dp[first - 1][second - 1].value + 1, Pair(first - 1, second - 1))
            } else {
                dp[first][second] = if (dp[first - 1][second].value > dp[first][second - 1].value) {
                    DPValue(dp[first - 1][second].value, Pair(first - 1, second))
                } else {
                    DPValue(dp[first][second - 1].value, Pair(first, second - 1))
                }
            }
        }
    }
    return dp
}

/* Function adds changed (added and deleted) lines to a given list*/

private fun addLines(dText: MutableList<DiffLine>, from: Int, to: Int, type: LineType) {
    for (i in from downTo to) {
        dText.add(DiffLine(type))
    }
}

/* Function restores the LCS by dp data*/

fun buildDiffText(firstText: Text, secondText: Text, dp: List<List<DPValue>>): MutableList<DiffLine> {
    val dText: MutableList<DiffLine> = mutableListOf()
    var indexes = Pair(firstText.text.size, secondText.text.size)
    do {
        val previous = dp[indexes.first][indexes.second].previous
        var (first, second) = indexes
        first -= 1
        second -= 1
        if (first != -1 && second != -1 && firstText.text[first] == secondText.text[second]) {
            dText.add(DiffLine(LineType.Common))
        } else {
            addLines(dText, second, previous.second, LineType.Add)
            addLines(dText, first, previous.first, LineType.Delete)
        }
        indexes = previous
    } while (indexes != Pair(0, 0)) //(0, 0) base of dp
    dText.reverse()
    sortDiffLines(dText)
    countIndexes(dText)
    return dText
}

/* Function sorts the data for two types of blocks common lines and changed one.
*  Changed lines in the block (between two common blocks or ends of the text) first should be deleted and then added*/

private fun sortDiffLines(dText: MutableList<DiffLine>) {
    val sortDText: MutableList<DiffLine> = mutableListOf()
    val added: MutableList<DiffLine> = mutableListOf()
    val deleted: MutableList<DiffLine> = mutableListOf()

    /*Collect all changed lines and add them in the right order (first deleted, then added) between common lines*/

    for (i in 0 until dText.size) {
        when (dText[i].type) {
            LineType.Add -> added.add(dText[i])
            LineType.Delete -> deleted.add(dText[i])
            else -> {
                updateSortDText(sortDText, deleted)
                updateSortDText(sortDText, added)
                sortDText.add(dText[i])
            }
        }
    }
    updateSortDText(sortDText, deleted)
    updateSortDText(sortDText, added)
    for (i in 0 until sortDText.size) {
        dText[i] = sortDText[i]
    }
}

private fun updateSortDText(sortDText: MutableList<DiffLine>, updated: MutableList<DiffLine>) {
    updated.forEach {
        sortDText.add(it)
    }
    updated.clear()
}

/* Count indexes of lines by their new order*/

private fun countIndexes(dText: MutableList<DiffLine>) {
    var firstIndex = -1
    var secondIndex = -1
    for (i in 0 until dText.size) {
        val line = dText[i]
        when (line.type) {
            LineType.Delete -> firstIndex++
            LineType.Add -> secondIndex++
            else -> {
                firstIndex++
                secondIndex++
            }
        }
        dText[i].firstIndex = firstIndex
        dText[i].secondIndex = secondIndex
    }
}
data class DPValue(val value: Int = 0, val previous: Pair<Int, Int> = Pair(0, 0))

/* Function calculates an array of dp for LongestCommonSubsequence*/

fun countLongestCommonSubsequence(firstText: Text, secondText: Text): List<List<DPValue>> {
    val firstSize = firstText.text.size
    val secondSize = secondText.text.size
    val dp: List<MutableList<DPValue>> = List(firstSize + 1) { MutableList(secondSize + 1) { DPValue() } }
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
        dText.add(DiffLine(type))
    }
}

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
    buildIndexes(dText)
    return dText
}

fun sortDiffLines(dText: MutableList<DiffLine>) {
    val sortDText: MutableList<DiffLine> = mutableListOf()
    val added: MutableList<DiffLine> = mutableListOf()
    val deleted: MutableList<DiffLine> = mutableListOf()
    for (i in 0 until dText.size) {
        if (dText[i].type == LineType.Common) {
            updateSortDText(sortDText, deleted)
            updateSortDText(sortDText, added)
            sortDText.add(dText[i])
            continue
        }
        when (dText[i].type) {
            LineType.Add -> added.add(dText[i])
            else -> deleted.add(dText[i])
        }
    }
    updateSortDText(sortDText, deleted)
    updateSortDText(sortDText, added)
    for (i in 0 until sortDText.size) {
        dText[i] = sortDText[i]
    }
}

fun updateSortDText(sortDText: MutableList<DiffLine>, updated: MutableList<DiffLine>) {
    updated.forEach {
        sortDText.add(it)
    }
    updated.clear()
}

fun buildIndexes(dText: MutableList<DiffLine>) {
    var firstIndex = -1
    var secondIndex = -1
    for (i in 0 until dText.size) {
        val line = dText[i]
        when (line.type) {
            LineType.Delete -> firstIndex += 1
            LineType.Add -> secondIndex += 1
            else -> {
                firstIndex += 1
                secondIndex += 1
            }
        }
        dText[i].firstIndex = firstIndex
        dText[i].secondIndex = secondIndex
    }
}
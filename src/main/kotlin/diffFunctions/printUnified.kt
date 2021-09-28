import java.lang.Integer.max
import java.lang.Math.min

/* Match lines that should be in the result */

fun printedLines(dText: List<DiffLine>, border: Int): List<Boolean> {
    val printed: MutableList<Boolean> = MutableList(dText.size) { false }
    for (i in dText.indices) {
        if (dText[i].type != LineType.Common) {
            val range = max(0, i - border)..dText.lastIndex.coerceAtMost(i + border)
            for (j in range) {
                printed[j] = true
            }
        }
    }
    return printed
}

/* Convert indices to the right format */

fun indexLength(type: Char, first: Int, last: Int): StringBuilder {
    return StringBuilder("$type${first + 1},${last - first + 1} ")
}

/*  Make description line "@@ firstIndex,firstLength secondIndex,secondLength @@
*   first line of each text and is number that would be printed*/

fun descriptionLine(first: List<DiffLine>, last: DiffLine): StringBuilder {
    val line = StringBuilder()
    line.append("@@ ")
    val deleteFirst = first.find { it.type != LineType.Add }?.firstIndex ?: 0
    line.append(indexLength('-', deleteFirst, last.firstIndex))
    val addFirst = first.find { it.type != LineType.Delete }?.secondIndex ?: 0
    line.append(indexLength('+', addFirst, last.secondIndex))
    line.appendLine("@@")
    return line
}

/* Return lines of the block in the right format */

fun printUnifiedBlock(block: MutableList<DiffLine>, texts: Texts): StringBuilder {
    if (block.size == 0) {
        return StringBuilder("")
    }
    val result = descriptionLine(block, block[block.lastIndex])
    block.forEach {
        result.append(when (it.type) {
            LineType.Add -> "+ "
            LineType.Delete -> "- "
            else -> "  "
        })
        result.appendLine(when (it.type) {
            LineType.Add -> texts.second[it.secondIndex]
            else -> texts.first[it.firstIndex]
        })
    }
    return result
}

fun printUnified(diff: List<DiffLine>, texts: Texts, border: Int = 3): String {
    val result = StringBuilder()
    val printed: List<Boolean> = printedLines(diff, border)
    val block: MutableList<DiffLine> = mutableListOf()  /* Contain lines that should be printed
                                                               Changed lines are separated by unprinted ones*/
    for (i in diff.indices) {
        if (!printed[i]) {
            result.append(printUnifiedBlock(block, texts))
            block.clear()
        } else {
            block.add(diff[i])
        }
    }
    result.append(printUnifiedBlock(block, texts))
    return result.toString()
}



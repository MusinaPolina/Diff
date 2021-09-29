import java.lang.StringBuilder

/* Return indexes of first and last changed lines that needed to description */

private fun linesIndexes(first : Int, last : Int): String {
    return if (first == last) {
        (first + 1).toString()
    } else {
        "${(first + 1)},${(last + 1)}"
    }
}

/* Return a line that contains a description of a given block:
*  number of lines (in both files) that were changed and type */

fun descriptionLine(deleted: List<DiffLine>, added: List<DiffLine>, plus: LineType): StringBuilder {
    val result = StringBuilder()
    result.append(linesIndexes(deleted[0].firstIndex, deleted[deleted.lastIndex].firstIndex))
    result.append(plus.value)
    result.appendLine(linesIndexes(added[0].secondIndex, added[added.lastIndex].secondIndex))
    return result
}

/* printOriginal{type of change} functions return output format for the whole block of lines
    (description + change lines)*/

private fun printOriginalAdded(added: List<DiffLine>, texts: Texts): StringBuilder {
    val result: StringBuilder = descriptionLine(added, added, LineType.Add)
    result.append(addedLines(added, texts))
    return result
}

private fun printOriginalDeleted(deleted: List<DiffLine>, texts: Texts): StringBuilder {
    val result: StringBuilder = descriptionLine(deleted, deleted, LineType.Delete)
    result.append(deletedLines(deleted, texts))
    return result
}

private fun printOriginalChange(deleted: List<DiffLine>, added: List<DiffLine>, texts: Texts): StringBuilder {
    val result: StringBuilder = descriptionLine(deleted, added, LineType.Common) 
    result.append(deletedLines(deleted, texts))
    result.appendLine("---")
    result.append(addedLines(added, texts))
    return result
}

/* Return added lines with '>' in the beginning*/

private fun addedLines(added: List<DiffLine>, texts: Texts): Any {
    val result = StringBuilder()
    added.forEach {
        result.append("> ")
        result.appendLine(texts.second[it.secondIndex])
    }
    return result
}

/* Return deleted lines with '<' in the beginning*/

private fun deletedLines(deleted: List<DiffLine>, texts: Texts): Any {
    val result = StringBuilder()
    deleted.forEach {
        result.append("< ")
        result.appendLine(texts.first[it.firstIndex])
    }
    return result
}

/* Return lines in one of the possible formats: added lines, deleted lines and changed one*/

private fun printOriginalBlock(lst: List<DiffLine>, texts: Texts): StringBuilder {
    val (added, deleted) = lst.partition { it.type == LineType.Add }
    return when {
        added.isNotEmpty() && deleted.isNotEmpty() -> printOriginalChange(deleted, added, texts)
        added.isNotEmpty() -> printOriginalAdded(added, texts)
        deleted.isNotEmpty() -> printOriginalDeleted(deleted, texts)
        else -> StringBuilder()
    }
}

/* Return a string. It contains all output (equal to the Original one)*/

fun printOriginal(diff: List<DiffLine>, texts: Texts): StringBuilder {
    val result = StringBuilder()
    val block: MutableList<DiffLine> = mutableListOf()  /* Contains added and deleted lines that should be printed
                                                            Changed lines are separated by common ones*/
    for (i in diff.indices) {
        when (diff[i].type) {
            LineType.Common -> {
                result.append(printOriginalBlock(block, texts))
                block.clear()
            }
            else -> block.add(diff[i])
        }
    }
    result.append(printOriginalBlock(block, texts))
    return result
}
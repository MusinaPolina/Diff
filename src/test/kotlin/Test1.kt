import kotlin.test.*

internal class Test1 {

    @Test
    fun testLCS1() {
        val text1 = listOf("this\n", "is\n", "original", "text\n")
        val text2 = listOf("this\n", "is\n", "new", "text\n")
        val result = countLongestCommonSubsequence(text1, text2)
        assertEquals(3, result[text1.size][text2.size].value) //test of numerical value of the LCS
        val answer: List<List<DiffLine>> = listOf(
            listOf(DiffLine(LineType.Common, 0, 0),
                DiffLine(LineType.Common, 1, 1),
                DiffLine(LineType.Common, 3, 3)),
            listOf(DiffLine(LineType.Add, 2, 2)),
            listOf(DiffLine(LineType.Delete, 2, 1)))
        compareAnswerLCS(answer, buildDiffText(text1, text2, result))
        val diff = Diff(text1, text2)
        assertEquals("3c3\n< original\n---\n> new\n", diff.printDefault())
    }

    @Test
    fun testLCS2() {
        val text1 = listOf("this", "is", "original", "text")
        val text2 = listOf("this", "text")
        val result = countLongestCommonSubsequence(text1, text2)
        assertEquals(2, result[text1.size][text2.size].value) //test of numerical value of the LCS
        val answer: List<List<DiffLine>> = listOf(
            listOf(DiffLine(LineType.Common, 0, 0),
                DiffLine(LineType.Common, 3, 1)),
            listOf(),
            listOf(DiffLine(LineType.Delete, 1, 0),
                DiffLine(LineType.Delete, 2, 0)))
        compareAnswerLCS(answer, buildDiffText(text1, text2, result))
        val diff = Diff(text1, text2)
        assertEquals("2,3d1\n< is\n< original\n", diff.printDefault())
    }

    @Test
    fun testLCS3() {
        val text1 = listOf("this\n", "is\n", "original\n", "text\n")
        val text2 = listOf("now\n", "it's\n", "completely\n", "new\n", "one")
        val result = countLongestCommonSubsequence(text1, text2)
        assertEquals(0, result[text1.size][text2.size].value) //test of numerical value of the LCS
        val answer: List<List<DiffLine>> = listOf(
            listOf(),
            listOf(DiffLine(LineType.Add, 3, 0),
                DiffLine(LineType.Add, 3, 1),
                DiffLine(LineType.Add, 3, 2),
                DiffLine(LineType.Add, 3, 3),
                DiffLine(LineType.Add, 3, 4)),
            listOf(DiffLine(LineType.Delete, 0, -1),
                DiffLine(LineType.Delete, 1, -1),
                DiffLine(LineType.Delete, 2, -1),
                DiffLine(LineType.Delete, 3, -1)))
        compareAnswerLCS(answer, buildDiffText(text1, text2, result))
    }

    private fun buildListDiffLines(result: List<DiffLine>): List<List<DiffLine>>{
        val list: MutableList<MutableList<DiffLine>> =
            mutableListOf(mutableListOf(), mutableListOf(), mutableListOf())
        for (it in result) {
            when (it.type) {
                LineType.Common -> list[0].add(it)
                LineType.Add -> list[1].add(it)
                else -> list[2].add(it)
            }
        }
        return list
    }

    private fun compareAnswerLCS(answer: List<List<DiffLine>>, result: MutableList<DiffLine>) {
        val current = buildListDiffLines(result)
        assertEquals(answer, current)
    }

    @Test
    fun testLCS4() {
        val text1 = listOf("aaa\n", "bvv\n", "att\n", "byy\n")
        val text2 = listOf("aaa\n", "bvv\n", "byy\n", "att\n")
        val result = countLongestCommonSubsequence(text1, text2)
        assertEquals(3, result[text1.size][text2.size].value)
    }

    @Test
    fun testLCS5() {
        val text1 = listOf("this", "is", "new", "original", "text")
        val text2 = listOf("this", "is", "test", "not", "TEXT")
        val diff = Diff(text1, text2)
        assertEquals("@@ -1,5 +1,5 @@\n  this\n  is\n- new\n- original\n- text\n+ test\n+ not\n+ TEXT\n", diff.printUnified())
        assertEquals("@@ -2,4 +2,4 @@\n  is\n- new\n- original\n- text\n+ test\n+ not\n+ TEXT\n", diff.printUnified(1))
    }
}

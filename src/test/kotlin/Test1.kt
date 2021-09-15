import kotlin.test.*

internal class Test1 {

    @Test
    fun testValueCountLongestCommonSubsequence() {
        var text1 = Text(listOf("this\n", "is\n", "original\n", "text\n"))
        var text2 = Text(listOf("this\n", "is\n", "new\n", "text\n"))
        var result = countLongestCommonSubsequence(text1, text2)
        assertEquals(3, result[text1.text.size][text2.text.size].value)

        text1 = Text(listOf("this\n", "is\n", "original\n", "text\n"))
        text2 = Text(listOf("this\n", "text\n"))
        result = countLongestCommonSubsequence(text1, text2)
        assertEquals(2, result[text1.text.size][text2.text.size].value)

        text1 = Text(listOf("this\n", "is\n", "original\n", "text\n"))
        text2 = Text(listOf("now\n", "it's\n", "completely\n", "new\n", "one"))
        result = countLongestCommonSubsequence(text1, text2)
        assertEquals(0, result[text1.text.size][text2.text.size].value)

        text1 = Text(listOf("aaa\n", "bvv\n", "att\n", "byy\n"))
        text2 = Text(listOf("aaa\n", "bvv\n", "byy\n", "att\n"))
        result = countLongestCommonSubsequence(text1, text2)
        assertEquals(3, result[text1.text.size][text2.text.size].value)
    }
}

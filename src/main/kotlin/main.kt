fun go() {
    println("Введите ссылку на первый файл:")
    val link1: String? = readLine()
    println("Введите ссылку на второй файл:")
    val link2: String? = readLine()
    if (link1 != null && link2 != null) {
        val text1 = Text(link1)
        val text2 = Text(link2)
        val diff = Diff(text1, text2)
        //print(diff.printDefault())
        print(diff.printUnified())
    } else {
        print("Упс, что то пошло не так")
    }
}

fun main() {
    go()
}

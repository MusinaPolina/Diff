import java.io.File

fun ask(): Boolean {
    println("Введите ссылку на первый файл:")
    val link1: String? = readLine()
    println("Введите ссылку на второй файл:")
    val link2: String? = readLine()
    if (link1 != null && link2 != null) {
        val file1 = File(link1)
        val file2 = File(link2)
        if (!file1.exists() || !file2.exists()) {
            println("Упс, что-то пошло не так\nПопробуйте еще раз")
            return true
        }
        val text1 = Text(file1)
        val text2 = Text(file2)
        val diff = Diff(text1, text2)
        println("Какой вы хотите формат?\nДефолтный: введите d\nУниверсальный: введите u")
        val format: String = readLine()!!
        when (format) {
            "d" -> print(diff.printDefault())
            "u" -> print(diff.printUnified())
            else -> {
                println("Упс, что-то пошло не так\nПопробуйте еще раз")
                return true
            }
        }

    } else {
        print("Упс, что-то пошло не так\nПопробуйте еще раз")
        return true
    }
    return true
}

fun example() {
    val text1 = Text(File("src/main/kotlin/text1.txt"))
    val text2 = Text(File("src/main/kotlin/text2.txt"))
    val diff = Diff(text1, text2)
    println("Дефолтный вывод:")
    println(diff.printDefault())
    println("Универсальный вывод:")
    println(diff.printUnified())
}

fun sayHello() {
    println("Хотите ли посмотреть примеры вывода? да/нет")
    val input = readLine()
    if (input == "да") {
        example()
    }
}

fun main() {
    sayHello()
    while (ask()) {

    }
}

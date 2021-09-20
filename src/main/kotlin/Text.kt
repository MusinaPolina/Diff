import java.io.File

/*
* class Text contains list of strings for comparison.
* Strings supposed to be received from input.
*/

class Text {
    var text: List<String> = listOf()
    constructor() {

    }
    constructor(path: String) {
        text = File(path).readLines()
    }
    constructor(input: List<String>) {
        text = input
    }
}
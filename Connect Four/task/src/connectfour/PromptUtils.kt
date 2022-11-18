package connectfour

val dimensionsRegex = Regex("\\s*\\d+\\s*[xX]\\s*\\d+\\s*")
val whitespaceRegex = Regex("\\s+")
val numberRegex = Regex("\\d+")

fun getNumberOfGames(): Int {
    while (true) {
        println("Do you want to play single or multiple games?\n" +
                "For a single game, input 1 or press Enter")
        println("Input a number of games:")
        val str = readln().trim()
        if (str.isBlank()) {
            return 1
        }
        if (numberRegex.matches(str)) {
            val intValue = str.toInt()
            if (intValue != 0) {
                return intValue
            }
        }
        println("Invalid input")
    }
}

fun getChar(firstPlayersTurn: Boolean): Char = if (firstPlayersTurn) FIRST_PLAYER_CHAR else SECOND_PLAYER_CHAR

fun getName(): String {
    return readln()
}

fun getDimensions(): Pair<Int, Int> {
    var rows: Int
    var columns: Int

    while (true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val line = readln()
        if (line.isEmpty()) {
            rows = 6
            columns = 7
            break
        }

        if (!dimensionsRegex.matches(line)) {
            println("Invalid input")
            continue
        }
        val fields = line.replace(whitespaceRegex, "").lowercase().split("x")
        rows = fields[0].toInt()
        columns = fields[1].toInt()
        if (!validDimension(rows)) {
            println("Board rows should be from 5 to 9")
            continue
        }
        if (!validDimension(columns)) {
            println("Board columns should be from 5 to 9")
            continue
        }
        break
    }
    return Pair(rows, columns)
}

fun validDimension(value: Int) : Boolean {
    return value in 5..9
}
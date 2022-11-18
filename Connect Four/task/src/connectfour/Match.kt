package connectfour

const val FIRST_PLAYER_CHAR = 'o'
const val SECOND_PLAYER_CHAR = '*'

class Match(
    private val firstPlayer: String, private val secondPlayer: String,
    private val matchNumber: Int, private val width: Int,
    private val height: Int, private val printNumber: Boolean
) {
    private val state: MutableList<MutableList<Char>> = mutableListOf()

    init {
        repeat(width) {
            state.add(mutableListOf())
        }
    }

    fun interactionLoop() : WinningCondition {
        if (printNumber) {
            println("Game #$matchNumber")
        }
        printBoard(width, height, state)
        var firstPlayerTurn = matchNumber % 2 == 1
        while (true) {
            printTurn(if (firstPlayerTurn) firstPlayer else secondPlayer)

            val input = readln()
            if (input == "end") {
                break
            }
            if (!validateTurn(input)) {
                println("Incorrect column number")
                continue
            }
            val inputNum = input.toInt()
            if (inputNum > width || inputNum < 1) {
                println("The column number is out of range (1 - ${width})")
                continue
            }
            val char = getChar(firstPlayerTurn)
            if (state[inputNum-1].size >= height) {
                println("Column $inputNum is full")
                continue
            }
            state[inputNum-1].add(char)

            firstPlayerTurn = !firstPlayerTurn
            printBoard(width, height, state)

            when (checkWinningCondition(width, height, state)) {
                WinningCondition.FIRST_WON -> {
                    println("Player $firstPlayer won")
                    return WinningCondition.FIRST_WON
                }
                WinningCondition.SECOND_WON -> {
                    println("Player $secondPlayer won")
                    return WinningCondition.SECOND_WON
                }
                WinningCondition.DRAW -> {
                    println("It is a draw")
                    return WinningCondition.DRAW
                }
                else -> {}
            }
        }
        return WinningCondition.NO_WINNER_YET
    }

    private fun checkWinningCondition(width: Int, height: Int, state: MutableList<MutableList<Char>>): WinningCondition {
        for (i in 0 until width) {
            val column = state[i]
            var sameInRow = 1
            for (j in 1 until column.size) {
                if (column[j] == column[j-1]) {
                    sameInRow++
                } else {
                    sameInRow = 1
                }
                if (sameInRow == 4) {
                    if (column[j] == FIRST_PLAYER_CHAR) {
                        return WinningCondition.FIRST_WON
                    }
                    return WinningCondition.SECOND_WON
                }
            }
        }

        for (i in 0 until height) {
            var sameInRow = 1
            for (j in 1 until width) {
                if (state[j-1].size > i && state[j].size > i && state[j-1][i] == state[j][i]) {
                    sameInRow++
                } else {
                    sameInRow = 1
                }
                if (sameInRow == 4) {
                    if (state[j][i] == FIRST_PLAYER_CHAR) {
                        return WinningCondition.FIRST_WON
                    }
                    return WinningCondition.SECOND_WON
                }
            }
        }

        for (i in 0..height-4) {
            var sameInRowLeftToRight = 1
            var sameInRowRightToLeft = 1
            for (j in 1 until height-i) {
                if (state[j-1].size > j+i-1 && state[j].size > j+i && state[j-1][j+i-1] == state[j][j+i]) {
                    sameInRowLeftToRight++
                } else {
                    sameInRowLeftToRight = 1
                }
                if (sameInRowLeftToRight == 4) {
                    if (state[j][j+i] == FIRST_PLAYER_CHAR) {
                        return WinningCondition.FIRST_WON
                    }
                    return WinningCondition.SECOND_WON
                }

                if (state[width-j].size > j+i-1 && state[width-j-1].size > j+i && state[width-j][j+i-1] == state[width-j-1][j+i]) {
                    sameInRowRightToLeft++
                } else {
                    sameInRowRightToLeft = 1
                }
                if (sameInRowRightToLeft == 4) {
                    if (state[width-j-1][j+i] == FIRST_PLAYER_CHAR) {
                        return WinningCondition.FIRST_WON
                    }
                    return WinningCondition.SECOND_WON
                }
            }
        }

        for (j in 1..width-4) {
            var sameInRowLeftToRight = 1
            var sameInRowRightToLeft = 1
            for (i in 0 until width-j-1) {
                if (state[j+i].size > j+i-1 && state[j+i+1].size > j+i && state[j+i][j+i-1] == state[j+i+1][j+i]) {
                    sameInRowLeftToRight++
                } else {
                    sameInRowLeftToRight = 1
                }
                if (sameInRowLeftToRight == 4) {
                    if (state[j+i+1][j+i] == FIRST_PLAYER_CHAR) {
                        return WinningCondition.FIRST_WON
                    }
                    return WinningCondition.SECOND_WON
                }

                if (state[width-j-i].size > j+i-1 && state[width-j-i-1].size > j+i && state[width-j-i][j+i-1] == state[width-j-i-1][j+i]) {
                    sameInRowRightToLeft++
                } else {
                    sameInRowRightToLeft = 1
                }
                if (sameInRowRightToLeft == 4) {
                    if (state[width-j-i-1][j+i] == FIRST_PLAYER_CHAR) {
                        return WinningCondition.FIRST_WON
                    }
                    return WinningCondition.SECOND_WON
                }
            }
        }

        val filledPlaces = state.sumOf { col -> col.size }
        if (filledPlaces >= width * height) {
            return WinningCondition.DRAW
        }

        return WinningCondition.NO_WINNER_YET

    }

    private fun printTurn(name: String) {
        println("$name's turn:")
    }

    private fun printBoard(width: Int, height: Int, contents: MutableList<MutableList<Char>>) {
        printNums(width)
        repeat(height) {
            printRow(width, height-it-1, contents)
        }
        printBottom(width)
    }

    private fun printNums(size: Int) {
        val str = java.lang.StringBuilder()
        for (i in 1..size){
            str.append(" ").append(i)
        }
        println(str.toString())
    }

    private fun printRow(size: Int, num: Int, contents: MutableList<MutableList<Char>>) {
        val str = java.lang.StringBuilder()
        repeat(size) {
            str.append("║").append(if (contents[it].size > num) contents[it][num] else ' ')
        }
        str.append("║")
        println(str.toString())
    }

    private fun printBottom(size: Int) {
        val str = java.lang.StringBuilder()
        str.append("╚═")
        str.append("╩═".repeat(size-1))
        str.append("╝")
        println(str.toString())
    }

    private fun validateTurn(input: String): Boolean {
        return numberRegex.matches(input)
    }

}
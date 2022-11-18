package connectfour

enum class WinningCondition {
    FIRST_WON,
    SECOND_WON,
    NO_WINNER_YET,
    DRAW
}

fun main() {
    println("Connect Four")
    println("First player's name:")
    val firstName = getName()
    println("Second player's name:")
    val secondName = getName()
    val dimensions = getDimensions()
    val numberOfGames = getNumberOfGames()

    println("$firstName VS $secondName")
    println("${dimensions.first} X ${dimensions.second} board")
    if (numberOfGames > 1) {
        println("Total $numberOfGames games")
    } else {
        println("Single game")
    }
    runGames(firstName, secondName, numberOfGames, dimensions.second, dimensions.first)
}

fun runGames(firstName: String, secondName: String, numberOfGames: Int, width: Int, height: Int) {
    var firstScore = 0
    var secondScore = 0
    for (i in 1..numberOfGames) {
        val match = Match(firstName, secondName, i, width, height, numberOfGames > 1)
        when (match.interactionLoop()) {
            WinningCondition.DRAW -> {
                firstScore++
                secondScore++
            }
            WinningCondition.FIRST_WON -> {
                firstScore+=2
            }
            WinningCondition.SECOND_WON -> {
                secondScore+=2
            }
            WinningCondition.NO_WINNER_YET -> {
                break
            }
        }
        if (i != numberOfGames) {
            println(
                "Score\n" +
                        "$firstName: $firstScore $secondName: $secondScore"
            )
        }
    }
    if (numberOfGames != 1) {
        println(
            "Score\n" +
                    "$firstName: $firstScore $secondName: $secondScore"
        )
    }
    println("Game over!")
}

package connectfour

const val GAMES_TITLE = "Connect Four"

const val Q_1ST_PLAYER_NAME = "First player's name:"
const val Q_2ND_PLAYER_NAME = "Second player's name:"
const val Q_SET_BOARD = "Set the board dimensions (Rows x Columns)\nPress Enter for default (6 x 7)"

const val DEFAULT_ROWS = 6
const val DEFAULT_COLUMNS = 7

const val E_BOARD_ROWS = "Board rows should be from 5 to 9"
const val E_BOARD_COLUMNS = "Board columns should be from 5 to 9"
const val E_INVALID_INPUT = "Invalid input"

fun main() {
    println(GAMES_TITLE)
    println(Q_1ST_PLAYER_NAME)
    val firstPlayerName = readln().filter { !it.isWhitespace() }
    println(Q_2ND_PLAYER_NAME)
    val secondPlayerName = readln().filter { !it.isWhitespace() }

    var boardSettings: String
    val regexBoardSettings = Regex("[5-9][xX][5-9]")
    var rows = DEFAULT_ROWS
    var columns = DEFAULT_COLUMNS
    while (true) {
        println(Q_SET_BOARD)
        boardSettings = readln().filter { !it.isWhitespace() }
        if (boardSettings.isEmpty()) break
        try {
            rows = boardSettings.split('x', 'X').first().toInt()
            columns = boardSettings.split('x', 'X').last().toInt()
        } catch (e: Throwable) {
            println(E_INVALID_INPUT)
            continue
        }
        if (!regexBoardSettings.matches(boardSettings)) {
            if (rows !in 5..9)
                println(E_BOARD_ROWS)
            else if (columns !in 5..9)
                println(E_BOARD_COLUMNS)
            else println(E_INVALID_INPUT)
        } else break
    }
    
    val result = "$firstPlayerName VS $secondPlayerName\n$rows X $columns board"
    println(result)
}

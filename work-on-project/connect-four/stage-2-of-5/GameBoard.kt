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

const val C_VERTICAL = '║'
const val C_RIGHT = '╚'
const val C_HORIZONTAL = '═'
const val C_CROSSED = '╩'
const val C_LEFT = '╝'

fun main() {
    // Greetings.
    println(GAMES_TITLE)
    println(Q_1ST_PLAYER_NAME)
    val firstPlayerName = readln().filter { !it.isWhitespace() }
    println(Q_2ND_PLAYER_NAME)
    val secondPlayerName = readln().filter { !it.isWhitespace() }

    val board = Board(DEFAULT_ROWS, DEFAULT_COLUMNS)
    setBoard(board)

    val result = "$firstPlayerName VS $secondPlayerName\n${board.rows} X ${board.columns} board"
    println(result)

    drawBoard(board)
}

data class Board(var rows: Int, var columns: Int)

fun setBoard(board: Board){
    var boardSettings: String
    val regexBoardSettings = Regex("[5-9][xX][5-9]")
    while (true) {
        println(Q_SET_BOARD)
        boardSettings = readln().filter { !it.isWhitespace() }
        if (boardSettings.isEmpty()) break
        try {
            board.rows = boardSettings.split('x', 'X').first().toInt()
            board.columns = boardSettings.split('x', 'X').last().toInt()
        } catch (e: Throwable) {
            println(E_INVALID_INPUT)
            continue
        }
        if (!regexBoardSettings.matches(boardSettings)) {
            if (board.rows !in 5..9)
                println(E_BOARD_ROWS)
            else if (board.columns !in 5..9)
                println(E_BOARD_COLUMNS)
            else println(E_INVALID_INPUT)
        } else break
    }
}

fun drawBoard(board: Board) {
    for (i in 1..board.columns) {
        print(" $i")
    }
    print('\n')
    repeat(board.rows) {
        repeat(board.columns + 1) { print("$C_VERTICAL ") }
        print('\n')
    }
    for (i in 1..board.columns * 2 + 1) {
        if (i == 1 || i == board.columns * 2 + 1) {
            if (i == 1) print(C_RIGHT)
            if (i == board.columns * 2 + 1) print(C_LEFT)
            continue
        }
        if (i % 2 == 0) print(C_HORIZONTAL)
        else print(C_CROSSED)
    }
}

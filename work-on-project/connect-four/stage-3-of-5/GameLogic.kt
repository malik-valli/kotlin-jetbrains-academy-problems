package connectfour

// <editor-fold desc="Constants">
const val GAMES_TITLE = "Connect Four"
const val END_TEXT = "Game over!"

const val DEFAULT_ROWS = 6
const val DEFAULT_COLUMNS = 7

const val Q_1ST_PLAYER_NAME = "First player's name:"
const val Q_2ND_PLAYER_NAME = "Second player's name:"
const val Q_SET_BOARD =
    "Set the board dimensions (Rows x Columns)\nPress Enter for default ($DEFAULT_ROWS x $DEFAULT_COLUMNS)"

const val E_BOARD_ROWS = "Board rows should be from 5 to 9"
const val E_BOARD_COLUMNS = "Board columns should be from 5 to 9"
const val E_INVALID_INPUT = "Invalid input"
const val E_INCORRECT_COLUMN = "Incorrect column number"
const val E_COLUMN_OUT_OF_RANGE = "The column number is out of range"

const val C_VERTICAL = '║'
const val C_RIGHT = '╚'
const val C_HORIZONTAL = '═'
const val C_CROSSED = '╩'
const val C_LEFT = '╝'
const val C_FIRST_PLAYER = 'o'
const val C_SECOND_PLAYER = '*'
const val C_EMPTY_POSITION = ' '

const val CMD_END = "end"
// </editor-fold>

var firstPlayerName: String = ""
var secondPlayerName: String = ""
var board = Board(DEFAULT_ROWS, DEFAULT_COLUMNS)
val emptyMatrix = { board: Board -> Array(board.columns) { Array(board.rows) { C_EMPTY_POSITION } } }

fun main() {
    // Greetings.
    println(GAMES_TITLE)
    println(Q_1ST_PLAYER_NAME)
    firstPlayerName = readln().filter { !it.isWhitespace() }
    println(Q_2ND_PLAYER_NAME)
    secondPlayerName = readln().filter { !it.isWhitespace() }

    // Settings.
    setBoard()
    val settings = "$firstPlayerName VS $secondPlayerName\n${board.rows} X ${board.columns} board"
    println(settings)
    drawBoard()

    play()
}

data class Board(var rows: Int, var columns: Int)

@JvmName("gameSetBoard")
fun setBoard() {
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

fun drawBoard(matrix: Array<Array<Char>> = emptyMatrix(board)) {
    for (i in 1..board.columns) {
        print(" $i")
    }
    print('\n')
    for (i in 1..board.rows) {
        for (j in 1..board.columns * 2 + 1) {
            if (j % 2 == 1) {
                print(C_VERTICAL)
            } else {
                print(matrix[j / 2 - 1][board.rows - i])
            }
        }
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
    print('\n')
}

fun play() {
    var currentPlayer = firstPlayerName
    val matrix = emptyMatrix(board)
    while (true) {
        println("$currentPlayer's turn:")
        val command = readln()
        if (command == CMD_END) {
            println(END_TEXT)
            break
        }
        val turn: Int
        try {
            turn = command.toInt()
        } catch (e: Throwable) {
            println(E_INCORRECT_COLUMN)
            continue
        }
        if (turn !in 1..board.columns) {
            println(E_COLUMN_OUT_OF_RANGE + " (1 - ${board.columns})")
            continue
        }
        if (matrix[turn - 1].last() != C_EMPTY_POSITION) {
            println("Column $turn is full")
            continue
        }
        val freePosition = matrix[turn - 1].indexOfFirst { position -> position == C_EMPTY_POSITION }
        matrix[turn - 1][freePosition] = if (currentPlayer == firstPlayerName) C_FIRST_PLAYER else C_SECOND_PLAYER
        drawBoard(matrix)
        currentPlayer = if (currentPlayer == firstPlayerName) secondPlayerName else firstPlayerName
    }
}

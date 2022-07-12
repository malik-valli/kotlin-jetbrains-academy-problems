// Link to the problem - hyperskill.org/learn/step/15133

const val EMPTY_STRING = ""

fun main() {
    val matrix = Array(2) { Array(3) { EMPTY_STRING } }
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            matrix[i][j] = "[$i][$j]"
        }
    }
    println(matrix.contentDeepToString())
}

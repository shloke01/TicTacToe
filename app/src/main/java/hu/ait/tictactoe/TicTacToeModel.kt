package hu.ait.tictactoe

object TicTacToeModel {

    const val EMPTY: Short = 0
    const val CIRCLE: Short = 1
    const val CROSS: Short = 2

    const val NOTOVER: Short = 4
    const val TIE: Short = 3

    private val model = arrayOf(
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY)
    )

    private var nextPlayer = CIRCLE

    fun resetModel() {
        for (i in 0..2) {
            for (j in 0..2) {
                model[i][j] = EMPTY
            }
        }
        nextPlayer = CIRCLE
    }

    fun getFieldContent(x: Int, y: Int) = model[x][y]

    fun setFieldContent(x: Int, y: Int, content: Short){
        model[x][y] = content
    }

    fun getNextPlayer() = nextPlayer

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
    }

    fun getWinner() : Short {

        // Check winner along rows
        for (i in 0..2) {
            val curr = model[i][0]

            if (curr == EMPTY){
                continue
            }

            for (j in 1..2) {
                if (model[i][j] != curr){
                    break
                }

                if (j == 2) {
                    return curr
                }
            }
        }

        // Check winner along columns
        for (i in 0..2) {
            val curr = model[0][i]

            if (curr == EMPTY){
                continue
            }

            for (j in 1..2) {
                if (model[j][i] != curr){
                    break
                }

                if (j == 2) {
                    return curr
                }
            }
        }

        // Check winner along diagonals
        if (model[1][1] == EMPTY){
            return NOTOVER
        }

        if ((model[0][0] == model[1][1] && model [2][2] == model[1][1]) ||
                model[0][2] == model[1][1] && model[2][0] == model[1][1]){
            return model[1][1]
        }

        // Check if there are still any empty cells
        for (i in 0..2) {
            for (j in 0..2) {
                if (model[i][j] == EMPTY){
                    return NOTOVER
                }
            }
        }

        // If no empty cells, it is a tie
        return TIE
    }

}
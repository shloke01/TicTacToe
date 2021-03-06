package hu.ait.tictactoe

import android.content.Context
import android.graphics.*
import android.os.SystemClock
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground: Paint
    private var paintLine: Paint
    private var paintX: Paint
    private var paintO: Paint
    private var runChronometer: Boolean = true

    init {
        paintBackground = Paint()
        paintBackground.color = Color.DKGRAY
        paintBackground.style = Paint.Style.FILL

        paintX = Paint()
        paintX.color = Color.CYAN
        paintX.style = Paint.Style.STROKE
        paintX.strokeWidth = 5f

        paintO = Paint()
        paintO.color = Color.RED
        paintO.style = Paint.Style.STROKE
        paintO.strokeWidth = 5f

        paintLine = Paint()
        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawGameArea(canvas!!)
        drawPlayers(canvas!!)

        if (runChronometer){
            (context as MainActivity).startCounter()
        }
    }

    private fun drawGameArea(canvas: Canvas) {
        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine)
        canvas.drawLine(0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine)

        // two vertical lines
        canvas.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine)
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - 2

                    canvas.drawCircle(centerX, centerY, radius.toFloat(), paintO)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), paintX)

                    canvas.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), paintX)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){

            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height / 3)

            if (tX < 3 && tY < 3 && TicTacToeModel.getFieldContent(tX, tY) ==
                TicTacToeModel.EMPTY) {
                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())
                TicTacToeModel.changeNextPlayer()

                if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CIRCLE){
                    (context as MainActivity).showText("Next player is O")
                } else  {
                    (context as MainActivity).showText("Next player is X")
                }


                if (TicTacToeModel.getWinner() == TicTacToeModel.CIRCLE) {

                    (context as MainActivity).stopCounter()
                    runChronometer = false

                    MaterialAlertDialogBuilder(context as MainActivity)
                        .setMessage("Player O wins. Resetting board for next game.")
                        .setPositiveButton("OK"){
                            dialog, which -> resetGame()
                        }
                        .show()

                } else if (TicTacToeModel.getWinner() == TicTacToeModel.CROSS){

                    (context as MainActivity).stopCounter()
                    runChronometer = false

                    MaterialAlertDialogBuilder(context as MainActivity)
                        .setMessage("Player X wins. Resetting board for next game.")
                        .setPositiveButton("OK"){
                                dialog, which -> resetGame()
                        }
                        .show()




                } else if (TicTacToeModel.getWinner() == TicTacToeModel.TIE){

                    (context as MainActivity).stopCounter()
                    runChronometer = false

                    MaterialAlertDialogBuilder(context as MainActivity)
                        .setMessage("The game ended in a tie. Resetting board for next game.")
                        .setPositiveButton("OK"){
                                dialog, which -> resetGame()
                        }
                        .show()
                }

                invalidate()
            }
        }

        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    fun resetGame(){
        (context as MainActivity).showText("The next player is 0")
        TicTacToeModel.resetModel()
        runChronometer = true
        invalidate()
    }

}
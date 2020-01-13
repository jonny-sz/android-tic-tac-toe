package com.jonnydev.tictactoe.models

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class WinnerLineView(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private var isWin = false
    private var mStartX: Float = 0F
    private var mStartY: Float = 0F
    private var mStopX: Float = 0F
    private var mStopY: Float = 0F
    private val paint = Paint().apply {
        strokeWidth = 10F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isWin) {
            canvas?.drawLine(mStartX, mStartY, mStopX, mStopY, paint)
        }
    }

    fun drawWinnerLine(
        startX: Float, startY: Float, stopX: Float, stopY: Float,
        color: Int = Color.BLACK
    ) {
        if (!isWin) {
            mStartX = startX
            mStartY = startY
            mStopX = stopX
            mStopY = stopY
            isWin = true
            paint.color = color
            invalidate()
        }
    }

    fun removeWinnerLine() {
        if (isWin) {
            paint.color = Color.TRANSPARENT
            invalidate()
            isWin = false
        }
    }
}

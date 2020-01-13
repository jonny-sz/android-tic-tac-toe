package com.jonnydev.tictactoe.models


import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView


class CellView(context: Context) : AppCompatTextView(context) {
    var isFilled = false
    var symb: Char? = null
        set(value) {
            field = value
            text = value.toString()
            isFilled = true
        }

    init {
        val shapeDrawable = ShapeDrawable().apply {
            shape = RectShape()
            paint.color = Color.GRAY
            paint.strokeWidth = 5f
            paint.style = Paint.Style.STROKE
        }

        background = shapeDrawable
        setTextColor(Color.BLACK)
        gravity = Gravity.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        setMeasuredDimension(width, width)
    }

    fun reset() {
        symb = ' '
        isFilled = false
    }
}

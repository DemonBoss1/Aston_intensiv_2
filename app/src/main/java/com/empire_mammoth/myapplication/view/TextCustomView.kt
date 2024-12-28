package com.empire_mammoth.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class TextCustomView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {
    private val paintBorder = Paint()
    private val paintText = Paint()
    private var text = ""

    init {
        paintBorder.style = Paint.Style.STROKE
        paintBorder.color = Color.BLACK

        paintText.style = Paint.Style.FILL
        paintText.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawCenter(canvas)
    }

    private fun drawCenter(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        //getDate()
        val rect = Rect()
        paintText.textSize = width.coerceAtMost(height) / 8f
        paintText.getTextBounds(text, 0, text.length, rect)

        canvas.drawRect(0f, 0f, width - 1f, height- 1f, paintBorder)
        canvas.drawText(
            text,
            centerX - rect.exactCenterX(),
            centerY - rect.exactCenterY(),
            paintText
        )
    }

    public fun clearText() {
        text = ""
        invalidate()
    }

    fun setText(nexText: String) {
        text = nexText
        invalidate()
    }
}
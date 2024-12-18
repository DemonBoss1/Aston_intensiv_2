package com.empire_mammoth.myapplication.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.icu.text.SimpleDateFormat
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.empire_mammoth.myapplication.R
import java.util.Date

class TextCustomView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {
    private val paintText = Paint()

    init {
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
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val text = currentDate.toString()
        val rect = Rect()
        paintText.textSize = width.coerceAtMost(height) / 4f
        paintText.getTextBounds(text, 0, text.length, rect)

        canvas.drawText(
            text,
            centerX - rect.exactCenterX(),
            centerY - rect.exactCenterY(),
            paintText
        )
    }
}
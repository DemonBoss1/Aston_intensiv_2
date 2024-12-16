package com.empire_mammoth.myapplication.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.empire_mammoth.myapplication.R

class WheelFortuneView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {
    private val paint = Paint()
    private val paintBorder = Paint()
    private val paintButton = Paint()

    private val colors = listOf(
        Color.RED,
        0xFFFFA500.toInt(),
        Color.YELLOW,
        Color.GREEN,
        Color.CYAN,
        Color.BLUE,
        0xFF8b00ff.toInt()
    )

    private val angle = 360f / colors.size
    private var startAngle = 90f
    private var scrollAngle = 0f

    private var isScroll = false
    private var indexScroll = 0

    private var currentSector = 3

    init {
        paint.style = Paint.Style.FILL
        paintButton.style = Paint.Style.FILL
        paintButton.color = Color.GRAY
        paintBorder.style = Paint.Style.STROKE
        paintBorder.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawWheelDrum(canvas)
        drawIndicator(canvas)
        drawCenter(canvas)

        if (isScroll) {
            startAngle += scrollAngle
            indexScroll++
            if (indexScroll > 99) isScroll = false
            invalidate()
        }

    }

    private fun drawCenter(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 6f
        canvas.drawCircle(centerX, centerY, radius, paintButton)
        canvas.drawCircle(centerX, centerY, radius, paintBorder)
    }

    private fun drawWheelDrum(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 2f

        colors.forEachIndexed { index, color ->
            paint.color = color
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                (startAngle + index * angle) % 360,
                angle,
                true,
                paint
            )
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                (startAngle + index * angle) % 360,
                angle,
                true,
                paintBorder
            )
        }
    }

    private fun drawIndicator(canvas: Canvas) {
        val centerX = width / 2f
        val bitmap = createBitmap()

        paint.color = Color.BLACK
        canvas.drawBitmap(bitmap, centerX - height / 10, 0f, paint)
    }

    private fun createBitmap(): Bitmap {
        val drawable = ContextCompat.getDrawable(context, R.drawable.baseline_arrow_drop_down_24)
        val bitmap = Bitmap.createBitmap(
            height / 5,
            height / 5,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, height / 5, height / 5)
        drawable?.draw(canvas)

        return bitmap
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isScroll) {
                    paintButton.color = Color.LTGRAY
                    val randomVar = (1..colors.size).random()
                    Log.e("ACTION_DOWN", randomVar.toString())
                    currentSector = (currentSector - randomVar + colors.size) % colors.size
                    Log.e("ACTION_DOWN", currentSector.toString())
                    scrollAngle = (360 + randomVar * angle) / 100
                    isScroll = true
                    indexScroll = 0
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP -> {
                paintButton.color = Color.GRAY
                invalidate()
            }
        }
        return true
    }
}
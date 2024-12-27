package com.empire_mammoth.myapplication.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
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
    private val paintText = Paint()

    private var scale = 0.75f

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
    private val numberScrollingFrames =100

    private var currentSector = 3

    init {
        paint.style = Paint.Style.FILL

        paintButton.style = Paint.Style.FILL
        paintButton.color = Color.GRAY

        paintBorder.style = Paint.Style.STROKE
        paintBorder.color = Color.BLACK

        paintText.style = Paint.Style.FILL
        paintText.color = Color.BLACK

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawWheelDrum(canvas)
        drawIndicator(canvas)
        drawCenter(canvas)

        scrollContinue()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isScroll) {
                    paintButton.color = Color.LTGRAY
                    startScroll()
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

    private fun drawCenter(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 6f  * scale
        val text = "Start"
        val rect = Rect()
        paintText.textSize = radius/4  * scale
        paintText.getTextBounds(text, 0, text.length, rect)

        canvas.drawCircle(centerX, centerY, radius, paintButton)
        canvas.drawCircle(centerX, centerY, radius, paintBorder)
        canvas.drawText(
            text,
            centerX - rect.exactCenterX(),
            centerY - rect.exactCenterY(),
            paintText
        )
    }

    private fun drawWheelDrum(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = width.coerceAtMost(height) / 2f  * scale

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
        canvas.drawBitmap(bitmap, centerX - height / 10 * scale, 0f, paint)
    }

    private fun createBitmap(): Bitmap {
        val drawable = ContextCompat.getDrawable(context, R.drawable.baseline_arrow_drop_down_24)
        val bitmap = Bitmap.createBitmap(
            (height / 5 * scale).toInt(),
            (height / 5 * scale).toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, (height / 5 * scale).toInt(), (height / 5 * scale).toInt())
        drawable?.draw(canvas)

        return bitmap
    }

    private fun startScroll() {
        val randomVar = (1..colors.size).random()
        currentSector = (currentSector - randomVar + colors.size) % colors.size
        scrollAngle = (720 + randomVar * angle) / numberScrollingFrames
        isScroll = true
        indexScroll = 0
    }

    private fun scrollContinue(){
        if (isScroll) {
            startAngle += scrollAngle
            indexScroll++
            if (indexScroll >= numberScrollingFrames) isScroll = false
            invalidate()
        }
    }
}
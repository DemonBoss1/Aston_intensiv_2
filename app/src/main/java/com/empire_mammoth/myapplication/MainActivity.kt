package com.empire_mammoth.myapplication

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.empire_mammoth.myapplication.databinding.ActivityMainBinding
import com.empire_mammoth.myapplication.view.WheelFortuneView
import com.squareup.picasso.Picasso
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var width = 640
    private var height = 360

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            wheelFortuneView.listener = object : WheelFortuneView.RollListener {
                var isUseImage = true
                override fun onStartRoll() {
                    if (isUseImage) {
                        Picasso.get()
                            .load("https://placebeard.it/$width" + "x$height")
                            .into(imageView)
                        width += 16
                        height += 9
                        isUseImage = false
                    }

                    textCustomView.clearText()
                    textCustomView.visibility = View.VISIBLE
                    imageView.visibility = View.GONE
                }

                override fun processingResults(result: Int) {
                    when (result % 2) {
                        Color.RED, Color.YELLOW, Color.CYAN, 0xFF8b00ff.toInt() -> {
                            val text = getDate()
                            textCustomView.setText(text)
                        }
                        0xFFFFA500.toInt(), Color.GREEN, Color.BLUE -> {
                            isUseImage = true

                            textCustomView.visibility = View.GONE
                            imageView.visibility = View.VISIBLE
                        }
                    }
                }

            }
            buttonReset.setOnClickListener {
                textCustomView.clearText()
                textCustomView.visibility = View.VISIBLE
                imageView.visibility = View.GONE

            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                private var value = 0.5f
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    value = p1 / 100f
                    wheelFortuneView.setScale(value)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}

                override fun onStopTrackingTouch(p0: SeekBar?) {}

            })
        }
    }

    private fun getDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate.toString()
    }
}
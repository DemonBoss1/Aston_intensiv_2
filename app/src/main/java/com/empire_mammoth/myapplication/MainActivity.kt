package com.empire_mammoth.myapplication

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.empire_mammoth.myapplication.databinding.ActivityMainBinding
import com.empire_mammoth.myapplication.view.WheelFortuneView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var width = 640
    private var height = 360

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            wheelFortuneView.listener = object : WheelFortuneView.Listener {
                var isUse = true
                override fun onStart() {
                    if(isUse){
                        Picasso.get()
                            .load("https://placebeard.it/$width" + "x$height")
                            .into(imageView)
                        width += 16
                        height += 9
                        isUse = false
                    }

                    textCustomView.clearText()
                    textCustomView.visibility = View.VISIBLE
                    imageView.visibility = View.GONE
                }

                override fun processingResults(result: Int) {
                    when(result%2) {
                        0-> {
                            textCustomView.getDate()
                        }
                        1 -> {
                            isUse = true

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
        }
    }
}
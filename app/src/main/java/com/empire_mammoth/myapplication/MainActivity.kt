package com.empire_mammoth.myapplication

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.empire_mammoth.myapplication.databinding.ActivityMainBinding
import com.empire_mammoth.myapplication.view.WheelFortuneView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            wheelFortuneView.listener = object : WheelFortuneView.Listener {
                override fun processingResults(result: Int) {
                    textCustomView.getDate()
                }

            }
buttonReset.setOnClickListener {
    textCustomView.clearText()
}
        }
    }
}
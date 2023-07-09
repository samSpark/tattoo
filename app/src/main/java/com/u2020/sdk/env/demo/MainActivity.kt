package com.u2020.sdk.env.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.u2020.sdk.env.Tattoo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Tattoo.o(this) {
            runOnUiThread {
                findViewById<TextView>(R.id.tattoo).text = it
            }
        }
    }

}
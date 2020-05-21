package com.example.kickly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Watching : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watching)
        title = resources.getString(R.string.watching)
    }
}

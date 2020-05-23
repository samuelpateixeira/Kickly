package com.example.kickly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)
        title = resources.getString(R.string.manage)

    }

}

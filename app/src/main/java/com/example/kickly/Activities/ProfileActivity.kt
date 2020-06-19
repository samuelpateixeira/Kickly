package com.example.kickly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.Classes.Kickly.Companion.checkData

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        title = resources.getString(R.string.profile)

        checkData()
    }
}

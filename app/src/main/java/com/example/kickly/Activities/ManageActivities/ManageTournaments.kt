package com.example.kickly.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.R

class ManageTournaments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tournaments)

        title = getString(R.string.manage_tournaments)
    }
}
package com.example.kickly

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset


class MainActivity : AppCompatActivity() {

    var buttonList = ArrayList<IconTextIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        //create intent for first page
        var firstpage = Intent(this,
            MainMenu::class.java //select first page here
        )

        startActivity(firstpage)
    }
}


package com.example.kickly.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kickly.IconTextActivity
import com.example.kickly.MainMenuActivity


class MainActivity : AppCompatActivity() {

    var buttonList = ArrayList<IconTextActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        context = applicationContext

        // create intent for Main Menu
        var mainMenu = Intent(this, MainMenuActivity::class.java)

        // start first page
        startActivity(mainMenu)



    }

    companion object {
        var context : Context? = null
            private set
    }
}


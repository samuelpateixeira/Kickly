package com.example.kickly.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kickly.Classes.Kickly.Companion.iconList
import com.example.kickly.Classes.Kickly.Companion.locationList
import com.example.kickly.Classes.Kickly.Companion.teamList
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.IconTextActivity
import com.example.kickly.KicklyTools
import com.example.kickly.MainMenuActivity


class MainActivity : AppCompatActivity() {

    var buttonList = ArrayList<IconTextActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        context = applicationContext

        tournamentList = KicklyTools.Generate.tournamentList(this)
        locationList = KicklyTools.Generate.locationList(this)
        teamList = KicklyTools.Generate.teamList(this)
        iconList = KicklyTools.Generate.iconList(this)


        for (i in 0 until (teamList.size)){
            teamList[i].location = locationList[i % (locationList.size)]
        }


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


package com.example.kickly.Classes

import android.content.Context
import android.graphics.drawable.Icon
import com.example.kickly.R
import com.example.kickly.Team
import com.example.kickly.Tournament

class Kickly : android.app.Application() {

    companion object {

        var tournamentList = ArrayList<Tournament>()
        var locationList = ArrayList<Location>()
        var iconList = ArrayList<Icon>()
        var teamList = ArrayList<Team>()
        var createCode = 1
        var editCode = 2
        var finishCode = 3
        var selectCode = 4
        var selectTeam1Code = 5
        var selectTeam2Code = 6
        var context : Context? = null


    }

}
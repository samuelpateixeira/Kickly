package com.example.kickly.Classes

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

    }

}
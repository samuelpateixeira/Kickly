package com.example.kickly.Classes

import android.graphics.drawable.Icon
import com.example.kickly.Team
import com.example.kickly.Tournament

class Kickly : android.app.Application() {

    companion object {

        var tournamentList = ArrayList<Tournament>()
        var locationList = ArrayList<Location>()
        var teamList = ArrayList<Team>()
        var iconList = ArrayList<Icon>()

    }

}
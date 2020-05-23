package com.example.kickly.Activities.WatchingActivities.TournamentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.R

class TeamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        title = getString(R.string.teams).capitalize()
    }
}

package com.example.kickly.Activities.WatchingActivities.TournamentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.R

class MatchPrognosis : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_prognosis)

        checkData()

        title = "Prognosis"

    }
}
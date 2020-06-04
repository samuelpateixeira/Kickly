package com.example.kickly.Activities.WatchingActivities.TournamentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_teams.*

class MatchesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)
        title = getString(R.string.matches).capitalize()

        var tournamentID = intent.extras!!.getInt("tournamentID")
        var tournamentList = KicklyTools.Generate.tournamentList(this)

        tournamentList[tournamentID].orderMatches()

        recyclerView.adapter = KicklyTools.Adapters.Matches(this, tournamentList[tournamentID].matches)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}

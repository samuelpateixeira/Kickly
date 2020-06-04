package com.example.kickly.Activities.WatchingActivities.TournamentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_teams.*

class TeamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        title = getString(R.string.teams).capitalize()

        var tournamentID = intent.extras!!.getInt("tournamentID")

        var tournamentList = KicklyTools.Generate.tournamentList(this)

        lvGroups.adapter = KicklyTools.Adapters.RecycleGroupsTeams(this, tournamentList[tournamentID].groupsArray())
        lvGroups.layoutManager = LinearLayoutManager(this)
    }
}

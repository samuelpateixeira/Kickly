package com.example.kickly.Activities.WatchingActivities.TournamentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_teams.*

class TeamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        title = getString(R.string.teams).capitalize()

        checkData()

        var tournamentID = intent.extras!!.getInt("tournamentID")

        if (tournamentList[tournamentID].registeredTeams.isNotEmpty()) {

            lvGroups.adapter =
                KicklyTools.Adapters.GroupsTeams(this, tournamentList[tournamentID].groupsArray())
            lvGroups.layoutManager = LinearLayoutManager(this)

        } else {
            lvGroups.visibility = View.GONE
            llNoTeamsRegistered.visibility = View.VISIBLE
        }
    }
}

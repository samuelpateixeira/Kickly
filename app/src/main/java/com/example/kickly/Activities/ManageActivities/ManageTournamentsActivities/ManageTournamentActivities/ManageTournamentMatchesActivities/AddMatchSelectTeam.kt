package com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.KicklyTools
import com.example.kickly.R
import com.example.kickly.Team
import kotlinx.android.synthetic.main.activity_manage_locations.*

class AddMatchSelectTeam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)

        checkData()

        title = getString(R.string.select_team)


        btnCreate.visibility = View.GONE

        var tournamentID = intent.extras!!.getInt("tournamentID")
        var group = intent.extras!!.getString("group")

        var registeredTeams = Kickly.tournamentList[tournamentID].group(group!!.first())
        var teams = ArrayList<Team>()

        for (registeredTeam in registeredTeams) {
            teams.add(registeredTeam.team)
        }

        recyclerView.adapter = KicklyTools.Adapters.AddMatchSelectTeams(this, teams)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
}
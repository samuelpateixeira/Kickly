package com.example.kickly.Activities.ManageActivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly.Companion.teamList
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import com.example.kickly.Team
import com.example.kickly.Tournament
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_manage_locations.recyclerView
import kotlinx.android.synthetic.main.activity_matches.*

class ManageTournamentTeam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)


        var tournamentID = intent.extras!!.getInt("tournamentID")

        var otherTeams = tournamentList[tournamentID].otherTeams()

        recyclerView.adapter = KicklyTools.Adapters.Teams(this, otherTeams)
        recyclerView.layoutManager = LinearLayoutManager(this)





    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var tournamentID = data!!.extras!!.getInt("tournamentID")
        var otherTeamsID = data!!.extras!!.getInt("otherTeamsID")
        var group = data!!.extras!!.getString("group")

        var otherTeams = tournamentList[tournamentID].otherTeams()

        tournamentList[tournamentID].registeredTeams.add(Tournament.RegisteredTeam(otherTeams[otherTeamsID], group!!.get(0)))

        finish()


    }
}
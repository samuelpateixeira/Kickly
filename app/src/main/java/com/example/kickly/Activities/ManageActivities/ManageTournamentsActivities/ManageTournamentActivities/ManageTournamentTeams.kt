package com.example.kickly.Activities.ManageActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Activities.MainActivity
import com.example.kickly.Activities.createCode
import com.example.kickly.Activities.editCode
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_manage_locations.recyclerView
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_manage_locations.message_empty as message_empty1
import kotlinx.android.synthetic.main.activity_matches.llNoMatchesScheduled as llNoMatchesScheduled1

class ManageTournamentTeams : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)

        title = getString(R.string.manage_teams)

        if (tournamentList.isEmpty()) {
            MainActivity.generate(this)
        }


        var tournamentID = intent.extras!!.getInt("tournamentID")

        var tournament = tournamentList[tournamentID]

        var groupsArray = tournament.groupsArray()

        if (tournamentList[tournamentID].registeredTeams.isNotEmpty()) {

            recyclerView.adapter = KicklyTools.Adapters.ManageTournamentTeams(this, groupsArray)
            recyclerView.layoutManager = LinearLayoutManager(this)

        } else {

            recyclerView.visibility = View.GONE
            llNoMatchesScheduled.visibility = View.VISIBLE
            message_empty.text = getString(R.string.tournament_no_teams_yet)

        }



        var createIntent = Intent(this, ManageTournamentTeam::class.java)

        createIntent.putExtra("tournamentID", tournamentID)
        createIntent.putExtra("requestCode", createCode)

        btnCreate.setOnClickListener { startActivityForResult(createIntent, createCode) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        finish()
        startActivity(intent)

    }
}
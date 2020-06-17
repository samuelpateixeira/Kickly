package com.example.kickly.Activities.ManageActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Activities.createCode
import com.example.kickly.Activities.editCode
import com.example.kickly.Classes.Kickly
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_manage_locations.recyclerView
import kotlinx.android.synthetic.main.activity_matches.*

class ManageTournamentTeams : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)

        var tournamentID = intent.extras!!.getInt("tournamentID")

        recyclerView.adapter = KicklyTools.Adapters.ManageTournamentTeams(this, Kickly.tournamentList[tournamentID].groupsArray())
        recyclerView.layoutManager = LinearLayoutManager(this)

        var createIntent = Intent(this, ManageTournamentTeam::class.java)

        createIntent.putExtra("tournamentID", tournamentID)
        createIntent.putExtra("requestCode", createCode)

        btnCreate.setOnClickListener { startActivity(createIntent) }

    }
}
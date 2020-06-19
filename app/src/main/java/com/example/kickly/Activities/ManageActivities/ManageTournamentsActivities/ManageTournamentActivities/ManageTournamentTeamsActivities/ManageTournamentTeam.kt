package com.example.kickly.Activities.ManageActivities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import com.example.kickly.Team
import com.example.kickly.Tournament
import kotlinx.android.synthetic.main.activity_manage_locations.*

class ManageTournamentTeam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var otherTeams = ArrayList<Team>()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)

        //MainActivity.generate(this)

        var tournamentID = intent.extras!!.getInt("tournamentID")

        otherTeams = tournamentList[tournamentID].otherTeams()

        recyclerView.adapter = KicklyTools.Adapters.Teams(this, otherTeams)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnCreate.setOnClickListener { startActivity(Intent(this, ManageTeam::class.java))
            (recyclerView.adapter as KicklyTools.Adapters.Teams).notifyDataSetChanged()
        }

        btnCreate.visibility = View.GONE

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        var tournamentID = data!!.extras!!.getInt("tournamentID")
        var otherTeamsID = data!!.extras!!.getInt("otherTeamsID")
        var group = data!!.extras!!.getString("group")!!

        var otherTeams = tournamentList[tournamentID].otherTeams()

        var newTeam = Tournament.RegisteredTeam(otherTeams[otherTeamsID], group.first())

        tournamentList[tournamentID].registeredTeams.add(newTeam)

        var resultIntent = Intent()

        resultIntent.putExtra("tournamentID", tournamentID)
        resultIntent.putExtra("otherTeamsID", otherTeamsID)
        resultIntent.putExtra("group", group)

        setResult(Activity.RESULT_OK, resultIntent)
        finish()


    }
}
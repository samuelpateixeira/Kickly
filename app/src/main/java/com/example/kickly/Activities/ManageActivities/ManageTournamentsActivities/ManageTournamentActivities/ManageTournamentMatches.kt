package com.example.kickly.Activities.ManageActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities.AddMatch
import com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities.AddMatchSelectGroup
import com.example.kickly.Activities.createCode
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_locations.*
import kotlinx.android.synthetic.main.activity_manage_locations.btnCreate
import kotlinx.android.synthetic.main.activity_manage_locations.llNoMatchesScheduled
import kotlinx.android.synthetic.main.activity_manage_locations.recyclerView
import kotlinx.android.synthetic.main.activity_manage_tournaments.*
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_manage_tournaments.message_empty as message_empty1
import kotlinx.android.synthetic.main.activity_matches.message_empty as message_empty1

class ManageTournamentMatches : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_locations)

        title = getString(R.string.manage_matches)

        var tournamentID = intent.extras!!.getInt("tournamentID")
        var tournament = tournamentList[tournamentID]

        tournament.orderMatches()

        var matches = tournament.matches

        if (tournamentList[tournamentID].matches.isNotEmpty()) {

            recyclerView.adapter = KicklyTools.Adapters.ManageTournamentMatches(this, matches, tournamentID)
            recyclerView.layoutManager = LinearLayoutManager(this)

        } else {

            recyclerView.visibility = View.GONE
            llNoMatchesScheduled.visibility = View.VISIBLE
            message_empty.text = getString(R.string.tournament_no_matches_yet)

        }



        var createIntent = Intent(this, AddMatchSelectGroup::class.java)

        createIntent.putExtra("tournamentID", tournamentID)

        btnCreate.setOnClickListener {
           startActivityForResult(createIntent, createCode)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        finish()
        startActivity(intent)

    }
}


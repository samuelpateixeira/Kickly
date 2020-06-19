package com.example.kickly.Activities.WatchingActivities.TournamentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_teams.*

class MatchesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)
        title = getString(R.string.matches).capitalize()

        checkData()

        var tournamentID = intent.extras!!.getInt("tournamentID")

        tournamentList[tournamentID].orderMatches()

        if (tournamentList[tournamentID].matches.isNotEmpty()) {

            recyclerView.adapter = KicklyTools.Adapters.Matches(
                this,
                tournamentList[tournamentID].matches,
                tournamentID
            )

            recyclerView.layoutManager = LinearLayoutManager(this)

        } else {
            recyclerView.visibility = View.GONE
            llNoMatchesScheduled.visibility = View.VISIBLE
        }
    }
}

package com.example.kickly.Activities.WatchingActivities

import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.Activities.WatchingActivities.TournamentActivities.MatchesActivity
import com.example.kickly.Activities.WatchingActivities.TournamentActivities.TeamsActivity
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.IconTextActivity
import com.example.kickly.KicklyTools
import com.example.kickly.R
import com.example.kickly.Tournament
import kotlinx.android.synthetic.main.activity_tournament.*

class TournamentActivity : AppCompatActivity() {

    //create a list of buttons (IconTextIntent)
    var buttonList = ArrayList<IconTextActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)

        checkData()

        // get intent extras
        var tournamentID = intent.extras!!.getInt("tournamentID")

        // get current tournament
        var currentTournament = tournamentList[tournamentID]

        // populate the views
        imgTournamentIcon.setImageIcon(currentTournament.icon)
        tvTournamentName.text = currentTournament.name
        tvCurrentStage.text = currentTournament.currentStage!!.toString(this)

        //region add the buttons to the list

        // Teams
        buttonList.add(
            IconTextActivity(
                Icon.createWithResource(
                    this, R.drawable.team), getResources().getString(R.string.teams).capitalize(),
                Intent(this, TeamsActivity::class.java).putExtra("tournamentID", tournamentID)
            )
        )

        //Matches
        buttonList.add(
            IconTextActivity(
                Icon.createWithResource(
                    this, R.drawable.match), getResources().getString(R.string.matches).capitalize(),
                Intent(this, MatchesActivity::class.java).putExtra("tournamentID", tournamentID)
            )
        )
        //endregion

        lvTournament.adapter = KicklyTools.Adapters.IconTextActivity(this, buttonList)

    }
}



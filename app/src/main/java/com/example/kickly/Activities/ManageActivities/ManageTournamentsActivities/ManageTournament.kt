package com.example.kickly.Activities.ManageActivities

import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.IconTextActivity
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_manage_tournament.*
import kotlinx.android.synthetic.main.activity_manage_tournaments.*
import kotlinx.android.synthetic.main.activity_manage_tournaments.listView

class ManageTournament : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tournament)

        var buttons = ArrayList<IconTextActivity>()

        var tournamentID = intent.extras!!.getInt("tournamentID")

        var tournament = tournamentList[tournamentID]

        imgTournamentIcon.setImageIcon(tournament.icon)
        tvTournamentName.text = tournament.name
        tvCurrentStage.text = tournament.currentStage!!.toString(this)



        title = tournamentList[tournamentID].name



        var manageTournamentTeams = Intent(this, ManageTournamentTeams::class.java)
        manageTournamentTeams.putExtra("tournamentID", tournamentID)

        var manageTournamentMatches = Intent(this, ManageTournamentMatches::class.java)
        manageTournamentMatches.putExtra("tournamentID", tournamentID)

        buttons.add(IconTextActivity(Icon.createWithResource(this, R.drawable.team ), getString(R.string.manage_teams), manageTournamentTeams))
        buttons.add(IconTextActivity(Icon.createWithResource(this, R.drawable.match ), getString(R.string.manage_matches), manageTournamentMatches))

        listView.adapter = KicklyTools.Adapters.IconTextActivity(this, buttons)
    }
}
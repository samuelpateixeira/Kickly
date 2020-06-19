package com.example.kickly.Activities.ManageActivities

import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kickly.Classes.Kickly.Companion.checkData
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

        checkData()

        var buttons = ArrayList<IconTextActivity>()

        var tournamentID = intent.extras!!.getInt("tournamentID")

        var manageTournamentTeams = Intent(this, ManageTournamentTeams::class.java)
        manageTournamentTeams.putExtra("tournamentID", tournamentID)

        var manageTournamentMatches = Intent(this, ManageTournamentMatches::class.java)
        manageTournamentMatches.putExtra("tournamentID", tournamentID)

        imgTournamentIcon.setImageIcon(tournamentList[tournamentID].icon)
        tvTournamentName.text = tournamentList[tournamentID].name
        tvCurrentStage.text = tournamentList[tournamentID].currentStage!!.toString(this)

        buttons.add(IconTextActivity(Icon.createWithResource(this, R.drawable.team ), getString(R.string.manage_teams), manageTournamentTeams))
        buttons.add(IconTextActivity(Icon.createWithResource(this, R.drawable.match ), getString(R.string.manage_matches), manageTournamentMatches))

        listView.adapter = KicklyTools.Adapters.IconTextActivity(this, buttons)
    }
}
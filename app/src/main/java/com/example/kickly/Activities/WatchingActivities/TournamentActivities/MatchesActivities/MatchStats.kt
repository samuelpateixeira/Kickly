package com.example.kickly.Activities.WatchingActivities.TournamentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_match_stats.*
import java.time.format.DateTimeFormatter

class MatchStats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_stats)

        checkData()

        title = getString(R.string.stats_capitalized)

        var tournamentID = intent.extras!!.getInt("tournamentID")
        var matchID = intent.extras!!.getInt("matchID")

        var match = tournamentList[tournamentID].matches[matchID]

        imgTeam1Icon.background = match.team1.team.icon.loadDrawable(this)
        imgTeam2Icon.background = match.team2.team.icon.loadDrawable(this)

        tvTeam1Name.text = match.team1.team.name
        tvTeam2Name.text = match.team2.team.name

        tvMatchResults.text = match.team1Score.toString() + " - " + match.team2Score.toString()


        if (match.isTie()!!) {
            imgTeam1Icon.setImageDrawable(getDrawable(R.drawable.stroke_yellow))
            imgTeam2Icon.setImageDrawable(getDrawable(R.drawable.stroke_yellow))
        } else if (match.winner() == match.team1) {
            imgTeam1Icon.setImageDrawable(getDrawable(R.drawable.stroke_green))
            imgTeam2Icon.setImageDrawable(getDrawable(R.drawable.stroke_red))
        } else if (match.winner() == match.team2) {
            imgTeam2Icon.setImageDrawable(getDrawable(R.drawable.stroke_green))
            imgTeam1Icon.setImageDrawable(getDrawable(R.drawable.stroke_red))
        }

        team1TotalAttempts.text = match.stats!!.team1TotalAttempts.toString()
        team2TotalAttempts.text = match.stats!!.team2TotalAttempts.toString()

        team1AttemptsOnTarget.text = match.stats!!.team1AttemptsOnTarget.toString()
        team2AttemptsOnTarget.text = match.stats!!.team2AttemptsOnTarget.toString()

        team1FoulsCommited.text = match.stats!!.team1FoulsCommited.toString()
        team2FoulsCommited.text = match.stats!!.team2FoulsCommited.toString()

        team1YellowCards.text = match.stats!!.team1YellowCards.toString()
        team2YellowCards.text = match.stats!!.team2YellowCards.toString()

        team1RedCards.text = match.stats!!.team1RedCards.toString()
        team2RedCards.text = match.stats!!.team2RedCards.toString()

        team1Offsides.text = match.stats!!.team1Offsides.toString()
        team2Offsides.text = match.stats!!.team2Offsides.toString()

        team1Corners.text = match.stats!!.team1Corners.toString()
        team2Corners.text = match.stats!!.team2Corners.toString()

        team1Possession.text = match.stats!!.team1Possession.toString() + "%"
        team2Possession.text = match.stats!!.team2Possession.toString() + "%"

        date.text = match.dateTime.format(DateTimeFormatter.ISO_DATE)
        time.text = match.dateTime.format(DateTimeFormatter.ISO_TIME)

        stage.text = match.team1.group.toString()

        location.text = match.location.name

    }
}
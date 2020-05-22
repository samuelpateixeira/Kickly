package com.example.kickly

import android.content.Context
import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_watching.*
import java.time.LocalDateTime
import java.time.Month

class Watching : AppCompatActivity() {

    var tournamentList = ArrayList<Tournament>()
    var teams = ArrayList<Team>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watching)
        title = resources.getString(R.string.watching)

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.drunk_fighters),
            "Drunk fighters"
            ))

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.kissers),
            "Kissers"
        ))

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.microsoft_windows),
            "Microsoft Windows"
        ))

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.android),
            "Android"
        ))

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.spotify),
            "Spotify"
        ))

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.apple),
            "Apple"
        ))

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.paypal),
            "PayPal"
        ))

        teams.add(Team(Icon.createWithResource(
            this, R.drawable.youtube),
            "Youtube"
        ))


        //region create Kickly 2020 tournament
        tournamentList.add(Tournament(
            Icon.createWithResource(
            this, R.drawable.futebol),
            "Kickly 2020",
            "group stage",
            Match( teams[0], teams[1], LocalDateTime.of(2020, Month.MAY, 18, 20, 45, 0, 0) ),
            Match( teams[2], teams[3], LocalDateTime.of(2020, Month.MAY, 27, 19, 45, 0, 0) )
            )
        )

        tournamentList[0].previousMatch!!.finish(1, 3)
        //endregion

        //region create Knight Tournament tournament
        tournamentList.add(Tournament(
            Icon.createWithResource(
                this, R.drawable.knight_tournament),
            "Knight Tournament",
            "group stage",
            Match( teams[4], teams[5], LocalDateTime.of(2020, Month.MAY, 1, 21, 45, 0, 0) ),
            Match( teams[6], teams[7], LocalDateTime.of(2020, Month.MAY, 22, 22, 30, 0, 0) )
        )
        )

        tournamentList[1].previousMatch!!.finish(4, 2)
        //endregion

        //region create Medal of Honor tournament
        tournamentList.add(Tournament(
            Icon.createWithResource(
                this, R.drawable.medal_of_honor),
            "Medal of Honor",
            "group stage",
            Match( teams[2], teams[3], LocalDateTime.of(2020, Month.MAY, 2, 21, 45, 0, 0) ),
            Match( teams[7], teams[4], LocalDateTime.of(2020, Month.MAY, 22, 20, 42, 0, 0) )
        )
        )

        tournamentList[2].previousMatch!!.finish(0, 0)
        //endregion


        lvTournaments.adapter = TournamentSummaryAdapter(this, tournamentList)

    }




}


class TournamentSummaryAdapter(context: Context, objects : ArrayList<Tournament>) : ArrayAdapter<Tournament>(context, R.layout.tournament_summary, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tournamentSummaryView = convertView

        // Check if the existing view is being reused, otherwise inflate the view
        if (tournamentSummaryView == null) {
            tournamentSummaryView = LayoutInflater.from(context).inflate(
                R.layout.tournament_summary, parent, false
            )
        }

        // get item
        var currentTournament = getItem(position)

        // populate the views

        //region find views
        var imgTournamentIcon = tournamentSummaryView!!.findViewById<ImageView>(R.id.imgTournamentIcon)
        var tvTournamentName = tournamentSummaryView.findViewById<TextView>(R.id.tvTournamentName)
        var tvCurrentStage = tournamentSummaryView.findViewById<TextView>(R.id.tvCurrentStage)
        var tvPreviousMatchResults = tournamentSummaryView.findViewById<TextView>(R.id.tvPreviousMatchResults)
        var imgPreviousMatchTeam1Icon = tournamentSummaryView.findViewById<ImageView>(R.id.imgPreviousMatchTeam1Icon)
        var imgPreviousMatchTeam2Icon = tournamentSummaryView.findViewById<ImageView>(R.id.imgPreviousMatchTeam2Icon)
        var imgNextMatchTeam1Icon = tournamentSummaryView.findViewById<ImageView>(R.id.imgNextMatchTeam1Icon)
        var imgNextMatchTeam2Icon = tournamentSummaryView.findViewById<ImageView>(R.id.imgNextMatchTeam2Icon)
        var tvNextMatchTimeLeft = tournamentSummaryView.findViewById<TextView>(R.id.tvNextMatchTimeLeft)
        //endregion

        imgTournamentIcon.setImageIcon(currentTournament!!.icon)
        tvTournamentName.text = currentTournament.name
        tvCurrentStage.text = currentTournament.currentStage
        tvPreviousMatchResults.text = currentTournament.previousMatch!!.team1Score.toString() + " - " + currentTournament.previousMatch!!.team2Score.toString()
        //imgPreviousMatchTeam1Icon.setImageIcon(currentTournament.previousMatch!!.team1!!.icon)
        imgPreviousMatchTeam1Icon.background = currentTournament.previousMatch!!.team1!!.icon.loadDrawable(context)
        //imgPreviousMatchTeam2Icon.setImageIcon(currentTournament.previousMatch!!.team2!!.icon)
        imgPreviousMatchTeam2Icon.background = currentTournament.previousMatch!!.team2!!.icon.loadDrawable(context)
        imgNextMatchTeam1Icon.setImageIcon(currentTournament.nextMatch!!.team1!!.icon)
        imgNextMatchTeam2Icon.setImageIcon(currentTournament.nextMatch!!.team2!!.icon)
        tvNextMatchTimeLeft.text = KicklyTools.timeLeft(currentTournament.nextMatch!!.dateTime!!, context)

        if (currentTournament.previousMatch!!.isTie()!!) {
            imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable( R.drawable.stroke_yellow ))
            imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable( R.drawable.stroke_yellow ))
        } else {
            if (currentTournament.previousMatch!!.winner() == currentTournament.previousMatch!!.team1) {
                imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable( R.drawable.stroke_green ))
                imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable( R.drawable.stroke_red ))
            } else if (currentTournament.previousMatch!!.winner() == currentTournament.previousMatch!!.team2) {
                imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable( R.drawable.stroke_red ))
                imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable( R.drawable.stroke_green ))
            } else {
                throw(Exception("nothing makes sense. There is no tie and no winner."))
            }
        }

        // set OnClickListener


        //
        //imgPreviousMatchTeam1Icon

        return tournamentSummaryView!!

    }


}


package com.example.kickly

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.kickly.Activities.WatchingActivities.TournamentActivity
import com.example.kickly.Classes.Stage
import kotlinx.android.synthetic.main.activity_main_list_item.view.*
import kotlinx.android.synthetic.main.list_view_groups_item.view.*
import kotlinx.android.synthetic.main.team_points_item.view.*
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset

class KicklyTools {

    companion object {

        // returns a string with the time left to the input date
        // example: in 5 days / in 3 hours / in 46 minutes / now
        fun timeLeft(futureTime: LocalDateTime, context: Context): String {
// Make sure we're running on Honeycomb or higher to use ActionBar APIs

            var timeLeftString: String?

            // get times and the times in EpochMilli (milliseconds since 1970)
            var currentTime = LocalDateTime.now()
            var futureTimeMilli = futureTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            var currentTimeMilli = currentTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            var differenceMilli = futureTimeMilli - currentTimeMilli

            // get total amount of remaining seconds, minutes, hours and days (27 hours instead of 3 hours and 1 day)
            val totalSeconds = differenceMilli / 1000
            val totalMinutes = totalSeconds / 60
            val totalHours = totalMinutes / 60
            val totalDays = totalHours / 24

            // get remaining time (3 hours and one day instead of 27 hours)
            val seconds = totalSeconds - (totalMinutes * 60)
            val minutes = totalMinutes - (totalHours * 60)
            val hours = totalHours - (totalDays * 24)
            val days = totalDays

            // get and format appropriate string
            if (days > 0) {
                timeLeftString = context.resources.getString(R.string.daysLeft, days)
            } else if (hours > 0) {
                timeLeftString = context.resources.getString(R.string.hoursLeft, hours)
            } else if (minutes > 0) {
                timeLeftString = context.resources.getString(R.string.minutesLeft, minutes)
            } else {
                timeLeftString = context.resources.getString(R.string.now)
            }

            return timeLeftString

        }

    }

    class Adapters {

        class TournamentSummary(context: Context, objects: ArrayList<Tournament>) :
            ArrayAdapter<Tournament>(context, R.layout.tournament_summary, objects) {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                // get recycled view
                var tournamentSummaryView = convertView

                // Check if the existing view is being reused
                if (tournamentSummaryView == null) {
                    // otherwise inflate the view
                    tournamentSummaryView = LayoutInflater.from(context).inflate(
                        R.layout.tournament_summary, parent, false
                    )
                }

                // get item
                var currentTournament = getItem(position)


                //region find views
                var imgTournamentIcon =
                    tournamentSummaryView!!.findViewById<ImageView>(R.id.imgTournamentIcon)
                var tvTournamentName =
                    tournamentSummaryView.findViewById<TextView>(R.id.tvTournamentName)
                var tvCurrentStage =
                    tournamentSummaryView.findViewById<TextView>(R.id.tvCurrentStage)
                var tvPreviousMatchResults =
                    tournamentSummaryView.findViewById<TextView>(R.id.tvPreviousMatchResults)
                var imgPreviousMatchTeam1Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgPreviousMatchTeam1Icon)
                var imgPreviousMatchTeam2Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgPreviousMatchTeam2Icon)
                var imgNextMatchTeam1Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgNextMatchTeam1Icon)
                var imgNextMatchTeam2Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgNextMatchTeam2Icon)
                var tvNextMatchTimeLeft =
                    tournamentSummaryView.findViewById<TextView>(R.id.tvNextMatchTimeLeft)
                //endregion

                //region populate the views

                imgTournamentIcon.setImageIcon(currentTournament!!.icon)
                tvTournamentName.text = currentTournament.name
                tvCurrentStage.text = currentTournament.currentStage!!.toString(context)
                tvPreviousMatchResults.text =
                    currentTournament.previousMatch!!.team1Score.toString() + " - " + currentTournament.previousMatch!!.team2Score.toString()
                imgPreviousMatchTeam1Icon.background =
                    currentTournament.previousMatch!!.team1!!.icon.loadDrawable(context)
                imgPreviousMatchTeam2Icon.background =
                    currentTournament.previousMatch!!.team2!!.icon.loadDrawable(context)
                imgNextMatchTeam1Icon.setImageIcon(currentTournament.nextMatch!!.team1!!.icon)
                imgNextMatchTeam2Icon.setImageIcon(currentTournament.nextMatch!!.team2!!.icon)
                tvNextMatchTimeLeft.text =
                    KicklyTools.timeLeft(currentTournament.nextMatch!!.dateTime!!, context)

                //set strokes according to winner/looser or tie
                // tie
                if (currentTournament.previousMatch!!.isTie()!!) {
                    imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                    imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                } else
                // team 1 winner
                    if (currentTournament.previousMatch!!.winner() == currentTournament.previousMatch!!.team1) {
                        imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                        imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                        // team 2 winner
                    } else if (currentTournament.previousMatch!!.winner() == currentTournament.previousMatch!!.team2) {
                        imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                        imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                    } else {
                        throw(Exception("nothing makes sense. There is no tie and no winner."))
                    }

                //endregion

                // configure intent
                var intent = Intent(context, TournamentActivity::class.java)
                intent.putExtra("tournamentID", position)

                // set OnClickListener
                tournamentSummaryView.setOnClickListener {
                    context.startActivity(intent)
                }

                return tournamentSummaryView!!

            }


        }

        class IconTextActivity(
            context: Context,
            objects: ArrayList<com.example.kickly.IconTextActivity>
        ) : ArrayAdapter<com.example.kickly.IconTextActivity>(
            context,
            R.layout.activity_main_list_item,
            objects
        ) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                // inflate the view
                var thisButton =
                    LayoutInflater.from(context)
                        .inflate(R.layout.activity_main_list_item, parent, false)

                // get current list item
                var currentIconTextActivity = getItem(position)!!

                // populate the views
                var imgIcon = thisButton.findViewById<ImageView>(R.id.imgIcon)
                imgIcon.setImageIcon(currentIconTextActivity.icon)
                thisButton.tvText.text = currentIconTextActivity.text

                // set on click listener
                thisButton.setOnClickListener {
                    context.startActivity(currentIconTextActivity.intent)
                }

                // return the view
                return thisButton

            }

        }

        class GroupsTeams(context: Context, objects: ArrayList<Tournament.Group>) :
            ArrayAdapter<Tournament.Group>(context, R.layout.list_view_groups_item, objects) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                // get recycled view
                var view = convertView

                // Check if the existing view is being reused
                if (view == null) {
                    // otherwise inflate the view
                    view = LayoutInflater.from(context).inflate(
                        R.layout.list_view_groups_item, parent, false
                    )
                }

                // get item
                var currentGroup = getItem(position)!!

                var tvGroup = view!!.findViewById<TextView>(R.id.tvGroup)

                tvGroup.text = context.getString(R.string.group_x, currentGroup.group)

                view.lvTeams.adapter = TeamPoints(context, currentGroup.teams)



                return view
            }

        }

        class TeamPoints(context: Context, objects: ArrayList<Tournament.RegisteredTeam>) :
            ArrayAdapter<Tournament.RegisteredTeam>(context, R.layout.team_points_item, objects) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                // get recycled view
                var view = convertView

                // Check if the existing view is being reused
                if (view == null) {
                    // otherwise inflate the view
                    view = LayoutInflater.from(context).inflate(
                        R.layout.team_points_item, parent, false
                    )
                }

                // get item
                var currentTeam = getItem(position)!!

                // region populate the view
                view!!

                var imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
                imgIcon.setImageIcon(currentTeam.team.icon)
                view.tvTeamName.text = currentTeam.team.name

                view.tvMatches.text = currentTeam.matches.toString()
                view.tvPoints.text = currentTeam.points.toString()
                view.tvGoalsScored.text = currentTeam.goalsScored.toString()
                view.tvGoalsConceded.text = currentTeam.goalsConceded.toString()
                view.tvGoalsDifference.text = currentTeam.goalsDifference().toString()

                var background = view.tvGoalsDifference.background

                view.tvGoalsDifference.setPadding(5,0,5,0)

                //endregion

                if ( currentTeam.goalsDifference() > 0 ) {

                    background.setTint(context.getColor(R.color.colorPrimary))
                    view.tvGoalsDifference.background = background

                } else if (currentTeam.goalsDifference() < 0) {

                    background.setTint(context.getColor(R.color.error))
                    view.tvGoalsDifference.background = background

                } else if (currentTeam.goalsDifference() == 0)  {

                    background.setTint(context.getColor(R.color.tie))
                    view.tvGoalsDifference.background = background

                }

                return view

            }

        }

    }
    class Generate {

        companion object {

            @RequiresApi(Build.VERSION_CODES.O)
            fun tournamentList(context: Context): ArrayList<Tournament> {
                var tournamentList = ArrayList<Tournament>()
                var teams = ArrayList<Team>()

                //region add teams

                // Drunk fighters
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.drunk_fighters
                        ),
                        "Drunk fighters"
                    )
                )

                // Kissers
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.kissers
                        ),
                        "Kissers"
                    )
                )

                // Microsoft Windows
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.microsoft_windows
                        ),
                        "Microsoft Windows"
                    )
                )

                // Android
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.android
                        ),
                        "Android"
                    )
                )

                // Spotify
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.spotify
                        ),
                        "Spotify"
                    )
                )

                // Apple
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.apple
                        ),
                        "Apple"
                    )
                )

                // PayPal
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.paypal
                        ),
                        "PayPal"
                    )
                )

                // Youtube
                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.youtube
                        ),
                        "Youtube"
                    )
                )

                //endregion

                //region create tournament Kickly 2020

                // create the tournament
                tournamentList.add(
                    Tournament(
                        Icon.createWithResource(context, R.drawable.futebol),
                        "Kickly 2020",
                        Stage.GROUPSTAGE,
                        Match(
                            teams[0],
                            teams[1],
                            LocalDateTime.of(2020, Month.MAY, 18, 20, 45, 0, 0)
                        ),
                        Match(
                            teams[2],
                            teams[3],
                            LocalDateTime.of(2020, Month.MAY, 27, 19, 45, 0, 0)
                        )
                    )
                )

                // finish the previous match
                tournamentList[0].previousMatch!!.finish(1, 3)

                //region add registered teams
                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.gabon
                            ), "Gabon"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams[0].matches = 2
                tournamentList[0].registeredTeams[0].points = 5
                tournamentList[0].registeredTeams[0].goalsScored = 8
                tournamentList[0].registeredTeams[0].goalsConceded = 5

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.gambia
                            ), "Gambia"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams[1].matches = 3
                tournamentList[0].registeredTeams[1].points = 6
                tournamentList[0].registeredTeams[1].goalsScored = 10
                tournamentList[0].registeredTeams[1].goalsConceded = 5

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.georgia
                            ), "Georgia"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams[2].matches = 3
                tournamentList[0].registeredTeams[2].points = 4
                tournamentList[0].registeredTeams[2].goalsScored = 2
                tournamentList[0].registeredTeams[2].goalsConceded = 5

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.germany
                            ), "Germany"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams[3].matches = 2
                tournamentList[0].registeredTeams[3].points = 5
                tournamentList[0].registeredTeams[3].goalsScored = 4
                tournamentList[0].registeredTeams[3].goalsConceded = 5


                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.ghana
                            ), "Ghana"
                        ), 'B'
                    )
                )

                tournamentList[0].registeredTeams[4].matches = 2
                tournamentList[0].registeredTeams[4].points = 7
                tournamentList[0].registeredTeams[4].goalsScored = 5
                tournamentList[0].registeredTeams[4].goalsConceded = 7


                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.gibraltar
                            ), "Gibraltar"
                        ), 'B'
                    )
                )


                tournamentList[0].registeredTeams[5].matches = 4
                tournamentList[0].registeredTeams[5].points = 12
                tournamentList[0].registeredTeams[5].goalsScored = 9
                tournamentList[0].registeredTeams[5].goalsConceded = 13



                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.greece
                            ), "Greece"
                        ), 'B'
                    )
                )



                tournamentList[0].registeredTeams[6].matches = 4
                tournamentList[0].registeredTeams[6].points = 11
                tournamentList[0].registeredTeams[6].goalsScored = 10
                tournamentList[0].registeredTeams[6].goalsConceded = 12



                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.greenland
                            ), "Greenland"
                        ), 'B'
                    )
                )


                tournamentList[0].registeredTeams[7].matches = 4
                tournamentList[0].registeredTeams[7].points = 14
                tournamentList[0].registeredTeams[7].goalsScored = 16
                tournamentList[0].registeredTeams[7].goalsConceded = 12


                //endregion

                //endregion

                //region create tournament Knight Tournament

                // create the tournament
                tournamentList.add(
                    Tournament(
                        Icon.createWithResource(
                            context, R.drawable.knight_tournament
                        ),
                        "Knight Tournament",
                        Stage.KNOCKOUTSTAGE,
                        Match(
                            teams[4],
                            teams[5],
                            LocalDateTime.of(2020, Month.MAY, 1, 21, 45, 0, 0)
                        ),
                        Match(
                            teams[6],
                            teams[7],
                            LocalDateTime.of(2020, Month.MAY, 23, 22, 30, 0, 0)
                        )
                    )
                )

                // finish the previous match
                tournamentList[1].previousMatch!!.finish(4, 2)

                //endregion

                //region create Medal of Honor tournament

                // create the tournament
                tournamentList.add(
                    Tournament(
                        Icon.createWithResource(context, R.drawable.medal_of_honor),
                        "Medal of Honor",
                        Stage.GROUPSTAGE,
                        Match(
                            teams[2],
                            teams[3],
                            LocalDateTime.of(2020, Month.MAY, 2, 21, 45, 0, 0)
                        ),
                        Match(
                            teams[7],
                            teams[4],
                            LocalDateTime.of(2020, Month.MAY, 23, 20, 42, 0, 0)
                        )
                    )
                )

                // finish the previous match
                tournamentList[2].previousMatch!!.finish(0, 0)

                //endregion

                return tournamentList
            }

            fun teams(context: Context): ArrayList<Team> {
                var teams = ArrayList<Team>()

                //region adding teams

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.gabon
                        ), "Gabon"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.georgia
                        ), "Georgia"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.gambia
                        ), "Gambia"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.germany
                        ), "Germany"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.ghana
                        ), "Ghana"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.gibraltar
                        ), "Gibraltar"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.greece
                        ), "Greece"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.greenland
                        ), "Greenland"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.grenada
                        ), "Grenada"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.guam
                        ), "Guam"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.guatemala
                        ), "Guatemala"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.guernsey
                        ), "Guernsey"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.guinea
                        ), "Guinea"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.guyana
                        ), "Guyana"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.haiti
                        ), "Haiti"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.hawaii
                        ), "Hawaii"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.honduras
                        ), "Honduras"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.hungary
                        ), "Hungary"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.iceland
                        ), "Iceland"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.india
                        ), "India"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.indonesia
                        ), "Indonesia"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.iran
                        ), "Iran"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.iraq
                        ), "Iraq"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.ireland
                        ), "Ireland"
                    )
                )

                teams.add(
                    Team(
                        Icon.createWithResource(
                            context, R.drawable.israel
                        ), "Israel"
                    )
                )

                //endregion

                return teams
            }
        }

    }

}


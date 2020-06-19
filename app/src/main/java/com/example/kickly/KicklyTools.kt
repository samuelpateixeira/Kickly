package com.example.kickly

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kickly.Activities.ManageActivities.ManageLocation
import com.example.kickly.Activities.ManageActivities.ManageTeam
import com.example.kickly.Activities.ManageActivities.ManageTournament
import com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities.AddMatch
import com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities.MatchFinish
import com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentTeamsActivities.SelectGroup
import com.example.kickly.Activities.WatchingActivities.TournamentActivities.MatchPrognosis
import com.example.kickly.Activities.WatchingActivities.TournamentActivities.MatchStats
import com.example.kickly.Activities.WatchingActivities.TournamentActivity
import com.example.kickly.Activities.editCode
import com.example.kickly.Classes.Kickly.Companion.createCode
import com.example.kickly.Classes.Kickly.Companion.finishCode
import com.example.kickly.Classes.Location
import kotlinx.android.synthetic.main.activity_main_list_item.view.*
import kotlinx.android.synthetic.main.icon.view.*
import kotlinx.android.synthetic.main.list_view_groups_item.view.*
import kotlinx.android.synthetic.main.match.view.*
import kotlinx.android.synthetic.main.team_points_item.view.*
import kotlinx.android.synthetic.main.tournament_summary.view.*
import kotlinx.android.synthetic.main.location.view.imgEdit
import kotlinx.android.synthetic.main.location.view.tvLocationName
import kotlinx.android.synthetic.main.match.view.button
import kotlinx.android.synthetic.main.team.view.*
import kotlinx.android.synthetic.main.team_points_item.view.tvTeamName
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlinx.android.synthetic.main.team.view.parentView as parentView1

class KicklyTools {

    companion object {

        // returns a Long with the time difference from time A to time B (timeB - timeA)
        // if timeB is greater, number is positive.
        fun timeDifference(timeA: LocalDateTime ,timeB: LocalDateTime): Long {

            // get times and the times in EpochMilli (milliseconds since 1970)
            var timeAMilli = timeA.toInstant(ZoneOffset.UTC).toEpochMilli()
            var timeBMilli = timeB.toInstant(ZoneOffset.UTC).toEpochMilli()
            var differenceMilli = timeBMilli - timeAMilli

            return differenceMilli

        }

        // returns a string with the time left to the input date
        // example: in 5 days / in 3 hours / in 46 minutes / now
        fun timeLeftString(futureTime: LocalDateTime, context: Context): String {
// Make sure we're running on Honeycomb or higher to use ActionBar APIs

            var timeLeftString: String?

            // get total amount of remaining seconds, minutes, hours and days (27 hours instead of 3 hours and 1 day)
            val totalSeconds = timeDifference(LocalDateTime.now() ,futureTime) / 1000
            val totalMinutes = totalSeconds / 60
            val totalHours = totalMinutes / 60
            val totalDays = totalHours / 24

            // get remaining time (3 hours and one day instead of 27 hours)
            //val seconds = totalSeconds - (totalMinutes * 60)
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

        open class TournamentSummary(context: Context, objects: ArrayList<Tournament>) :
            ArrayAdapter<Tournament>(context, R.layout.tournament_summary, objects) {

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

                //previous match
                var previousMatchView = tournamentSummaryView.findViewById<LinearLayout>(R.id.previous_match)
                var tvPreviousMatchResults =
                    tournamentSummaryView.findViewById<TextView>(R.id.tvPreviousMatchResults)
                var imgPreviousMatchTeam1Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgPreviousMatchTeam1Icon)
                var imgPreviousMatchTeam2Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgPreviousMatchTeam2Icon)

                //next match
                var nextMatchView = tournamentSummaryView.findViewById<LinearLayout>(R.id.next_match)
                var imgNextMatchTeam1Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgNextMatchTeam1Icon)
                var imgNextMatchTeam2Icon =
                    tournamentSummaryView.findViewById<ImageView>(R.id.imgNextMatchTeam2Icon)
                var tvNextMatchTimeLeft =
                    tournamentSummaryView.findViewById<TextView>(R.id.tvNextMatchTimeLeft)

                //no matches
                var tvNoMatchesScheduled = tournamentSummaryView.tvNoMatchesScheduled

                //endregion

                //region populate the views

                imgTournamentIcon.setImageIcon(currentTournament!!.icon)
                tvTournamentName.text = currentTournament.name
                tvCurrentStage.text = currentTournament.currentStage!!.toString(context)

                //previous match
                if (currentTournament.previousMatch() != null) {

                    tvPreviousMatchResults.text =
                        currentTournament.previousMatch()!!.team1Score.toString() + " - " + currentTournament.previousMatch()!!.team2Score.toString()
                    imgPreviousMatchTeam1Icon.background =
                        currentTournament.previousMatch()!!.team1!!.team.icon.loadDrawable(context)
                    imgPreviousMatchTeam2Icon.background =
                        currentTournament.previousMatch()!!.team2!!.team.icon.loadDrawable(context)

                    //set strokes according to winner/looser or tie
                    // tie
                    if (currentTournament.previousMatch()!!.isTie()!!) {
                        imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                        imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                    } else
                    // team 1 winner
                        if (currentTournament.previousMatch()!!.winner() == currentTournament.previousMatch()!!.team1) {
                            imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                            imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                            // team 2 winner
                        } else if (currentTournament.previousMatch()!!.winner() == currentTournament.previousMatch()!!.team2) {
                            imgPreviousMatchTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                            imgPreviousMatchTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                        } else {
                            throw(Exception("nothing makes sense. There is no tie and no winner."))
                        }

                    previousMatchView.visibility = View.VISIBLE

                } else {
                    previousMatchView.visibility = View.GONE
                }

                //next match
                if (currentTournament.nextMatch() != null) {

                    imgNextMatchTeam1Icon.setImageIcon(currentTournament.nextMatch()!!.team1!!.team.icon)
                    imgNextMatchTeam2Icon.setImageIcon(currentTournament.nextMatch()!!.team2!!.team.icon)
                    tvNextMatchTimeLeft.text =
                        timeLeftString(currentTournament.nextMatch()!!.dateTime!!, context)

                    nextMatchView.visibility = View.VISIBLE

                } else {
                    nextMatchView.visibility = View.GONE
                }

                //if there are no matches
                if (currentTournament.nextMatch() == null && currentTournament.previousMatch() == null) {
                    tvNoMatchesScheduled.visibility = View.VISIBLE
                } else {
                    tvNoMatchesScheduled.visibility = View.GONE
                }





                //endregion

                // configure intent
                var intent = Intent(context, TournamentActivity::class.java)
                intent.putExtra("tournamentID", position)

                // set OnClickListener
                tournamentSummaryView.setOnClickListener {
                    context.startActivity(intent)
                }

                return tournamentSummaryView

            }

        }

        class ManageTournamentsRV(context: Context, objects: ArrayList<Tournament>) :
            TournamentSummary(context, objects) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var view = super.getView(position, convertView, parent)

                view.imgEdit.visibility = View.VISIBLE

                //set click listener
                var clickIntent = Intent(context, ManageTournament::class.java)
                clickIntent.putExtra("tournamentID", position)
                clickIntent.putExtra("requestCode", editCode)

                //view.imgEdit.setOnClickListener { (context as Activity).startActivity(clickIntent) }
                view.setOnClickListener { (context as Activity).startActivity(clickIntent) }

                return view
            }
        }

        class IconTextActivity(
            context: Context,
            objects: ArrayList<com.example.kickly.IconTextActivity>
        ) : ArrayAdapter<com.example.kickly.IconTextActivity>(
            context,
            R.layout.activity_main_bigger_list_item,
            objects
        ) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                // inflate the view
                var thisButton =
                    LayoutInflater.from(context)
                        .inflate(R.layout.activity_main_bigger_list_item, parent, false)

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


        class GroupsTeams(var context: Context, var groups: ArrayList<Tournament.Group>) : RecyclerView.Adapter<GroupsTeams.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var tvGroup = itemView.tvGroup
                var rvTeams = itemView.rvTeams
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.list_view_groups_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return groups.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                var currentGroup = groups[position]
                holder.tvGroup.text = context.getString(R.string.group_x, currentGroup.group.toString())
                holder.rvTeams.adapter = TeamPoints(context, currentGroup.teams)
                holder.rvTeams.layoutManager = LinearLayoutManager(context)

            }


        }

        class TeamPoints(var context: Context, var teams: ArrayList<Tournament.RegisteredTeam>) :
            RecyclerView.Adapter<TeamPoints.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var imgIcon = itemView.findViewById<ImageView>(R.id.imgIcon)
                var tvTeamName = itemView.tvTeamName
                var tvMatches = itemView.tvMatches
                var tvPoints = itemView.tvPoints
                var tvGoalsScored = itemView.tvGoalsScored
                var tvGoalsConceded = itemView.tvGoalsConceded
                var tvGoalsDifference = itemView.tvGoalsDifference
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.team_points_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return teams.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current team
                var currentTeam = teams[position]

                //region populate the view

                holder.imgIcon.setImageIcon(currentTeam.team.icon)
                holder.tvTeamName.text = currentTeam.team.name

                holder.tvMatches.text = currentTeam.matches.toString()
                holder.tvPoints.text = currentTeam.points.toString()
                holder.tvGoalsScored.text = currentTeam.goalsScored.toString()
                holder.tvGoalsConceded.text = currentTeam.goalsConceded.toString()
                holder.tvGoalsDifference.text = currentTeam.goalsDifference().toString()


                holder.tvGoalsDifference.setPadding(5,0,5,0)

                //endregion

                //region set goalsDifference background color

                if ( currentTeam.goalsDifference() > 0 ) {
                    holder.tvGoalsDifference.background = context.getDrawable(R.drawable.circle_text_background_green)

                } else if (currentTeam.goalsDifference() < 0) {
                    holder.tvGoalsDifference.background = context.getDrawable(R.drawable.circle_text_background_red)

                } else if (currentTeam.goalsDifference() == 0)  {
                    holder.tvGoalsDifference.background = context.getDrawable(R.drawable.circle_text_background_yellow)
                }

                //endregion

            }

        }

        class ManageTournamentTeams(var context: Context, var groups: ArrayList<Tournament.Group>) : RecyclerView.Adapter<ManageTournamentTeams.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var tvGroup = itemView.tvGroup
                var rvTeams = itemView.rvTeams
                var text_view_m = itemView.text_view_m
                var text_view_pts = itemView.text_view_pts
                var text_view_gs = itemView.text_view_gs
                var text_view_gc = itemView.text_view_gc
                var text_view_dif = itemView.text_view_dif
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.list_view_groups_item, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return groups.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                var currentGroup = groups[position]
                holder.tvGroup.text = context.getString(R.string.group_x, currentGroup.group.toString())

                holder.text_view_m.visibility = View.GONE
                holder.text_view_pts.visibility = View.GONE
                holder.text_view_gs.visibility = View.GONE
                holder.text_view_gc.visibility = View.GONE
                holder.text_view_dif.visibility = View.GONE

                holder.rvTeams.adapter = ManageTournamentTeamsTeam(context, currentGroup.teams)
                holder.rvTeams.layoutManager = LinearLayoutManager(context)

            }

        }

        class ManageTournamentTeamsTeam(var context: Context, var teams: ArrayList<Tournament.RegisteredTeam>) :
            RecyclerView.Adapter<ManageTournamentTeamsTeam.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

                var imgIcon = itemView.findViewById<ImageView>(R.id.imgIcon)
                var tvTeamName = itemView.tvTeamName
                var tvMatches = itemView.tvMatches
                var tvPoints = itemView.tvPoints
                var tvGoalsScored = itemView.tvGoalsScored
                var tvGoalsConceded = itemView.tvGoalsConceded
                var tvGoalsDifference = itemView.tvGoalsDifference
                var imgEdit = itemView.imgEdit

            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {

                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.team_points_item, parent, false)
                return ItemViewHolder(view)

            }

            override fun getItemCount(): Int {
                return teams.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current team
                var currentTeam = teams[position]

                //region populate the view

                holder.imgIcon.setImageIcon(currentTeam.team.icon)
                holder.tvTeamName.text = currentTeam.team.name

                holder.tvMatches.visibility = View.GONE
                holder.tvPoints.visibility = View.GONE
                holder.tvGoalsScored.visibility = View.GONE
                holder.tvGoalsConceded.visibility = View.GONE
                holder.tvGoalsDifference.visibility = View.GONE

                //endregion

                //holder.imgEdit.visibility = View.VISIBLE

                //holder.imgEdit.setOnClickListener {  }

            }

        }

        class Matches(var context: Context, var matches: ArrayList<Match>, var tournamentID : Int) :
            RecyclerView.Adapter<Matches.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var finished = itemView.finished
                var imgTeam1Icon = itemView.imgTeam1Icon
                var tvTeam1Name = itemView.tvTeam1Name
                var tvMatchResults = itemView.tvMatchResults
                var tvTeam2Name = itemView.tvTeam2Name
                var imgTeam2Icon = itemView.imgTeam2Icon
                var date = itemView.date
                var stage = itemView.stage
                var location = itemView.location
                var button =  itemView.button
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.match, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return matches.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current match
                var currentMatch = matches[position]

                //region populate the view

                holder.imgTeam1Icon.background = currentMatch.team1!!.team.icon.loadDrawable(context)
                holder.imgTeam2Icon.background = currentMatch.team2!!.team.icon.loadDrawable(context)

                holder.tvTeam1Name.text = currentMatch.team1.team.name
                holder.tvTeam2Name.text = currentMatch.team2.team.name

                holder.stage.text = currentMatch.team1.group.toString()
                holder.location.text = currentMatch.location.name

                var buttonIntent = Intent()

                if (currentMatch.isFinished) {
                    holder.finished.visibility = View.VISIBLE

                    holder.tvMatchResults.text = currentMatch.team1Score.toString() + " - " + currentMatch.team2Score.toString()
                    holder.button.text = context.getString(R.string.stats)
                    buttonIntent = Intent(context, MatchStats::class.java)


                    if (currentMatch.isTie()!!) {
                        holder.imgTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                        holder.imgTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                    } else if (currentMatch.winner() == currentMatch.team1) {
                        holder.imgTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                        holder.imgTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                    } else if (currentMatch.winner() == currentMatch.team2) {
                        holder.imgTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                        holder.imgTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                    }

                    holder.date.text = currentMatch.dateTime.format(DateTimeFormatter.ISO_DATE)
                    //Log.e("date", "today is " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))


                } else {

                    holder.tvMatchResults.text = "VS"
                    holder.finished.visibility = View.GONE
                    holder.button.text = context.getString(R.string.prognosis)
                    buttonIntent = Intent(context, MatchPrognosis::class.java)
                    holder.date.text = timeLeftString(currentMatch.dateTime, context)
                    holder.imgTeam1Icon.setImageDrawable(null)
                    holder.imgTeam2Icon.setImageDrawable(null)

                }

                buttonIntent.putExtra("tournamentID", tournamentID )
                buttonIntent.putExtra("matchID", position)


                holder.button.setOnClickListener { context.startActivity(buttonIntent) }

                //endregion


            }

        }

        class ManageTournamentMatches(var context: Context, var matches: ArrayList<Match>, var tournamentID : Int) :
            RecyclerView.Adapter<ManageTournamentMatches.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var finished = itemView.finished
                var imgTeam1Icon = itemView.imgTeam1Icon
                var tvTeam1Name = itemView.tvTeam1Name
                var tvMatchResults = itemView.tvMatchResults
                var tvTeam2Name = itemView.tvTeam2Name
                var imgTeam2Icon = itemView.imgTeam2Icon
                var date = itemView.date
                var stage = itemView.stage
                var location = itemView.location
                var button =  itemView.button
                var parentView = itemView.parentView
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.match, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return matches.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current match
                var currentMatch = matches[position]

                //region populate the view
                holder.imgTeam1Icon.background = currentMatch.team1!!.team.icon.loadDrawable(context)
                holder.imgTeam2Icon.background = currentMatch.team2!!.team.icon.loadDrawable(context)

                holder.tvTeam1Name.text = currentMatch.team1.team.name
                holder.tvTeam2Name.text = currentMatch.team2.team.name

                holder.stage.text = currentMatch.team1.group.toString()
                holder.location.text = currentMatch.location.name

                var buttonIntent = Intent()

                if (currentMatch.isFinished) {
                    holder.finished.visibility = View.VISIBLE

                    holder.tvMatchResults.text = currentMatch.team1Score.toString() + " - " + currentMatch.team2Score.toString()
                    holder.button.text = context.getString(R.string.stats)
                    buttonIntent = Intent(context, MatchStats::class.java)


                    if (currentMatch.isTie()!!) {
                        holder.imgTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                        holder.imgTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_yellow))
                    } else if (currentMatch.winner() == currentMatch.team1) {
                        holder.imgTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                        holder.imgTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                    } else if (currentMatch.winner() == currentMatch.team2) {
                        holder.imgTeam2Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_green))
                        holder.imgTeam1Icon.setImageDrawable(context.getDrawable(R.drawable.stroke_red))
                    }

                    holder.date.text = currentMatch.dateTime.format(DateTimeFormatter.ISO_DATE)
                    //Log.e("date", "today is " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))


                } else {

                    holder.tvMatchResults.text = "VS"
                    holder.finished.visibility = View.GONE
                    holder.button.text = context.getString(R.string.finish).toUpperCase()
                    buttonIntent = Intent(context, MatchFinish::class.java)
                    holder.date.text = timeLeftString(currentMatch.dateTime, context)
                    holder.imgTeam1Icon.setImageDrawable(null)
                    holder.imgTeam2Icon.setImageDrawable(null)

                }

                buttonIntent.putExtra("tournamentID", tournamentID )
                buttonIntent.putExtra("matchID", position)


                holder.button.setOnClickListener { (context as Activity).startActivityForResult(buttonIntent, finishCode ) }

                //endregion


            }

        }


        class Locations(var context: Context, var locations: ArrayList<Location>) :
            RecyclerView.Adapter<Locations.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var tvLocationName: TextView = itemView.tvLocationName
                var imgEdit = itemView.imgEdit
                var createCode = 1
                var editCode = 2
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.location, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return locations.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current match
                var location = locations[position]

                //populate the view
                holder.tvLocationName.text = location.name

                //set click listener
                var clickIntent = Intent(context, ManageLocation::class.java)
                clickIntent.putExtra("locationID", position)
                clickIntent.putExtra("requestCode", editCode)

                holder.imgEdit.setOnClickListener { (context as Activity).startActivityForResult(clickIntent, holder.editCode) }


            }

        }

        open class TeamsEdit(var context: Context, var teams: ArrayList<Team>) :
            RecyclerView.Adapter<TeamsEdit.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var imgIcon = itemView.findViewById<ImageView>(R.id.imgIcon)
                var tvLocationName: TextView = itemView.tvLocationName
                var tvTeamName = itemView.tvTeamName
                var imgEdit = itemView.imgEdit
                var parentView = itemView.parentView
                var createCode = 1
                var editCode = 2
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.team, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return teams.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current match
                var team = teams[position]

                //populate the view
                holder.tvTeamName.text = team.name
                holder.tvLocationName.text = team.location!!.name
                holder.imgIcon.setImageIcon(team.icon)

                //set click listener
                var clickIntent = Intent(context, ManageTeam::class.java)
                clickIntent.putExtra("tournamentID", position)
                clickIntent.putExtra("requestCode", editCode)
                clickIntent.putExtra("teamID", position)

                holder.imgEdit.setOnClickListener { (context as Activity).startActivityForResult(clickIntent, editCode) }

            }

        }

        class Teams(var context: Context, var teams: ArrayList<Team>): RecyclerView.Adapter<Teams.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var imgIcon = itemView.findViewById<ImageView>(R.id.imgIcon)
                var tvLocationName: TextView = itemView.tvLocationName
                var tvTeamName = itemView.tvTeamName
                var imgEdit = itemView.imgEdit
                var parentView = itemView.parentView
                var createCode = 1
                var editCode = 2
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.team, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return teams.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current match
                var team = teams[position]

                //populate the view
                holder.tvTeamName.text = team.name
                holder.tvLocationName.text = team.location!!.name
                holder.imgIcon.setImageIcon(team.icon)

                holder.imgEdit.visibility = View.GONE

                var intentCreate = Intent(context, SelectGroup::class.java)
                var tournamentID = (context as Activity).intent.extras!!.getInt("tournamentID")
                intentCreate.putExtra("tournamentID", tournamentID)
                intentCreate.putExtra("otherTeamsID", position)

                holder.parentView.setOnClickListener {
                    (context as Activity).startActivityForResult(intentCreate, createCode) }

            }

        }

        class AddMatchSelectTeams(var context: Context, var teams: ArrayList<Team>): RecyclerView.Adapter<AddMatchSelectTeams.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var imgIcon = itemView.findViewById<ImageView>(R.id.imgIcon)
                var tvLocationName: TextView = itemView.tvLocationName
                var tvTeamName = itemView.tvTeamName
                var imgEdit = itemView.imgEdit
                var parentView = itemView.parentView
                var createCode = 1
                var editCode = 2
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.team, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return teams.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current match
                var team = teams[position]

                //populate the view
                holder.tvTeamName.text = team.name
                holder.tvLocationName.text = team.location!!.name
                holder.imgIcon.setImageIcon(team.icon)

                holder.imgEdit.visibility = View.GONE

                var resultIntent = Intent(context, SelectGroup::class.java)
                var tournamentID = (context as Activity).intent.extras!!.getInt("tournamentID")
                var requestCode = (context as Activity).intent.extras!!.getInt("requestCode")
                resultIntent.putExtra("tournamentID", tournamentID)
                resultIntent.putExtra("teamID", position)

                holder.parentView.setOnClickListener {
                    (context as Activity).setResult(requestCode, resultIntent)
                    (context as Activity).finish()
                     }

            }

        }

        class GroupsSelect(var context: Context, var groups: ArrayList<String>) :
            RecyclerView.Adapter<GroupsSelect.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var tvGroup = itemView.tvGroup
                var parentView = itemView.parentView
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.group, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return groups.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current group
                var group = groups[position]

                holder.tvGroup.text = group

                //set click listener
                var resultIntent = Intent()

                var tournamentID = (context as Activity).intent.extras!!.getInt("tournamentID")
                var otherTeamsID = (context as Activity).intent.extras!!.getInt("otherTeamsID")
                (context as Activity).intent.extras!!.getInt("requestCode")

                resultIntent.putExtra("tournamentID", tournamentID)
                resultIntent.putExtra("otherTeamsID", otherTeamsID)
                resultIntent.putExtra("group", groups[position].toString())


                holder.parentView.setOnClickListener {
                    (context as Activity).setResult(Activity.RESULT_OK, resultIntent)
                    (context as Activity).finish()
                }



            }

        }

        class AddMatchGroupsSelect(var context: Context, var groups: ArrayList<String>) :
            RecyclerView.Adapter<AddMatchGroupsSelect.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var tvGroup = itemView.tvGroup
                var parentView = itemView.parentView
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.group, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return groups.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                // get current group
                var group = groups[position]

                holder.tvGroup.text = group

                //set click listener
                var createIntent = Intent(context, AddMatch::class.java)

                var tournamentID = (context as Activity).intent.extras!!.getInt("tournamentID")
                var requestCode = (context as Activity).intent.extras!!.getInt("requestCode")

                createIntent.putExtra("tournamentID", tournamentID)
                createIntent.putExtra("group", group)
                createIntent.putExtra("requestCode", requestCode)



                holder.parentView.setOnClickListener {

                    (context as Activity).startActivity(createIntent)
                    (context as Activity).finish()
                }



            }

        }

        class IconPicker(var context: Context, var icons: ArrayList<Icon>) :
            RecyclerView.Adapter<IconPicker.ItemViewHolder>() {

            class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                var img = itemView.img
                var selectCode = 3
            }

            override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
                var inflater = LayoutInflater.from(context)
                var view = inflater.inflate(R.layout.icon, parent, false)
                return ItemViewHolder(view)
            }

            override fun getItemCount(): Int {
                return icons.size
            }

            override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

                var resultIntent = Intent()

                var icon = icons[position]

                //set click listener
                resultIntent.putExtra("iconID", position)
                resultIntent.putExtra("requestCode", holder.selectCode)

                holder.img.setImageIcon(icon)

                holder.img.setOnClickListener {

                    (context as Activity).setResult(Activity.RESULT_OK, resultIntent)
                    (context as Activity).finish()

                }

            }

        }

    }

    class Generate {

        companion object {


            /*
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
                            context, R.drawable.icon_kiss
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
                        Icon.createWithResource(context, R.drawable.football),
                        "Kickly 2020",
                        Stage.GROUPSTAGE
                    )
                )

                //region add registered teams
                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_airplane
                            ), "Gabon"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_battery
                            ), "Gambia"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_bed
                            ), "Georgia"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_building
                            ), "Germany"
                        ), 'A'
                    )
                )

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_case
                            ), "Ghana"
                        ), 'B'
                    )
                )

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_center
                            ), "Gibraltar"
                        ), 'B'
                    )
                )

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_climb
                            ), "Greece"
                        ), 'B'
                    )
                )

                tournamentList[0].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_clip
                            ), "Greenland"
                        ), 'B'
                    )
                )

                for (registeredTeam in tournamentList[0].registeredTeams) {
                    registeredTeam.team.location = Location( registeredTeam.team.name + " stadium")
                }

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[0],
                        tournamentList[0].registeredTeams[3],
                        LocalDateTime.of(2022, 6, 7, 20, 0),
                        Location("location")
                    )
                )

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[0],
                        tournamentList[0].registeredTeams[3],
                        LocalDateTime.of(2025, 6, 7, 20, 0),
                        Location("location")
                    )
                )

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[0],
                        tournamentList[0].registeredTeams[3],
                        LocalDateTime.of(2023, 6, 7, 20, 0),
                        Location("location")
                    )
                )

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[0],
                        tournamentList[0].registeredTeams[3],
                        LocalDateTime.of(2021, 6, 7, 20, 0),
                        Location("location")
                    )
                )

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[0],
                        tournamentList[0].registeredTeams[3],
                        LocalDateTime.of(2024, 6, 7, 20, 0),
                        Location("location")
                    )
                )

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[0],
                        tournamentList[0].registeredTeams[1],
                        LocalDateTime.of(2020, 5, 28, 20, 30),
                        Location("location")
                    )
                )

                tournamentList[0].matches.last().finish(3,2,
                    Stats(54,52,
                        63,54,
                        1,2,
                        3,2,
                        1,1,
                        3,4,
                        3,1,
                        48)

                )

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[1],
                        tournamentList[0].registeredTeams[0],
                        LocalDateTime.of(2020, 5, 30, 20, 45),
                        Location("location")
                    )
                )

                tournamentList[0].matches.last().finish(2,2,
                    Stats(65,47,
                        40,72,
                        4,3,
                        3,0,
                        0,2,
                        3,5,
                        2,1,
                        52)

                )


                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[0],
                        tournamentList[0].registeredTeams[2],
                        LocalDateTime.of(2020, 6, 2, 21, 0),
                        Location("location")
                    )
                )

                tournamentList[0].matches.last().finish(2,1,
                    Stats(57,65,
                        44,47,
                        1,3,
                        4,1,
                        1,1,
                        1,2,
                        4,3,
                        57)

                )

                tournamentList[0].matches.add(
                    Match(
                        tournamentList[0].registeredTeams[2],
                        tournamentList[0].registeredTeams[0],
                        LocalDateTime.of(2020, 6, 5, 20, 0),
                        Location("location")
                    )
                )

                for (match in tournamentList[0].matches) {
                    match.location = match.team1.team.location!!
                }






                //endregion

                //endregion

                //region create tournament Kotlin World Championship

                // create the tournament
                tournamentList.add(
                    Tournament(
                        Icon.createWithResource(context, R.drawable.spotify),
                        "Kotlin World Championship",
                        Stage.GROUPSTAGE
                    )
                )

                //region add registered teams
                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_compass
                            ), "Haiti"
                        ), 'A'
                    )
                )

                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_devices
                            ), "Hungary"
                        ), 'A'
                    )
                )


                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_drinks
                            ), "Guernsey"
                        ), 'A'
                    )
                )

                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_eye
                            ), "Honduras"
                        ), 'A'
                    )
                )

                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_eye_glasses
                            ), "Indonesia"
                        ), 'B'
                    )
                )

                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_glasses
                            ), "Israel"
                        ), 'B'
                    )
                )

                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_helicopter
                            ), "Guam"
                        ), 'B'
                    )
                )

                tournamentList[1].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_hot
                            ), "Gibraltar"
                        ), 'B'
                    )
                )

                for (registeredTeam in tournamentList[1].registeredTeams) {
                    registeredTeam.team.location = Location( registeredTeam.team.name + " stadium")
                }

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[0],
                        tournamentList[1].registeredTeams[3],
                        LocalDateTime.of(2020, 6, 27, 20, 0),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[3],
                        tournamentList[1].registeredTeams[0],
                        LocalDateTime.of(2020, 6, 9, 20, 30),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[0],
                        tournamentList[1].registeredTeams[2],
                        LocalDateTime.of(2020, 7, 7, 21, 0),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[2],
                        tournamentList[1].registeredTeams[0],
                        LocalDateTime.of(2020, 7, 7, 19, 30),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[0],
                        tournamentList[1].registeredTeams[1],
                        LocalDateTime.of(2020, 8, 7, 20, 0),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[1],
                        tournamentList[1].registeredTeams[0],
                        LocalDateTime.of(2020, 3, 28, 20, 30),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[0],
                        tournamentList[1].registeredTeams[1],
                        LocalDateTime.of(2020, 2, 10, 20, 45),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[0],
                        tournamentList[1].registeredTeams[4],
                        LocalDateTime.of(2020, 1, 2, 21, 0),
                        Location("location")
                    )
                )

                tournamentList[1].matches.add(
                    Match(
                        tournamentList[1].registeredTeams[4],
                        tournamentList[1].registeredTeams[0],
                        LocalDateTime.of(2020, 9, 5, 20, 0),
                        Location("location")
                    )
                )

                for (match in tournamentList[1].matches) {
                    match.location = match.team1.team.location!!
                }






                //endregion

                //endregion

                //region create tournament Basic Win

                // create the tournament
                tournamentList.add(
                    Tournament(
                        Icon.createWithResource(context, R.drawable.icon_profile_black),
                        "Basic Win",
                        Stage.GROUPSTAGE
                    )
                )

                //region add registered teams
                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_kiss
                            ), "hungary"
                        ), 'A'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_light
                            ), "Gibraltar"
                        ), 'A'
                    )
                )


                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_lightning
                            ), "Guyana"
                        ), 'A'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_money
                            ), "Haiti"
                        ), 'A'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_nature_person
                            ), "Honduras"
                        ), 'B'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_pizza
                            ), "Greenland"
                        ), 'B'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_reading
                            ), "Iran"
                        ), 'B'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_eye_glasses
                            ), "Greece"
                        ), 'B'
                    )
                )

                for (registeredTeam in tournamentList[2].registeredTeams) {
                    registeredTeam.team.location = Location( registeredTeam.team.name + " stadium")
                }

                tournamentList[2].matches.add(
                    Match(
                        tournamentList[2].registeredTeams[1],
                        tournamentList[2].registeredTeams[0],
                        LocalDateTime.of(2020, 3, 28, 20, 30),
                        Location("location")
                    )
                )

                tournamentList[2].matches.last().finish(2,2,
                    Stats(69,42,
                        32,68,
                        3,4,
                        2,1,
                        1,0,
                        3,4,
                        3,5,
                        65)

                )

                tournamentList[2].matches.add(
                    Match(
                        tournamentList[2].registeredTeams[0],
                        tournamentList[2].registeredTeams[1],
                        LocalDateTime.of(2020, 2, 10, 20, 45),
                        Location("location")
                    )
                )

                tournamentList[2].matches.last().finish(3,2,
                    Stats(65,45,
                        36,27,
                        3,3,
                        2,0,
                        0,1,
                        2,2,
                        3,7,
                        33)

                )


                tournamentList[2].matches.add(
                    Match(
                        tournamentList[2].registeredTeams[0],
                        tournamentList[2].registeredTeams[4],
                        LocalDateTime.of(2020, 1, 2, 21, 0),
                        Location("location")
                    )
                )

                tournamentList[2].matches.last().finish(1,3,
                    Stats(79,52,
                        42,78,
                        2,3,
                        4,0,
                        0,1,
                        2,4,
                        3,7,
                        44)
                )

                for (match in tournamentList[2].matches) {
                    match.location = match.team1.team.location!!
                }






                //endregion

                //endregion

                //region create tournament No Matches

                // create the tournament
                tournamentList.add(
                    Tournament(
                        Icon.createWithResource(context, R.drawable.youtube),
                        "No Matches",
                        Stage.GROUPSTAGE
                    )
                )

                //region add registered teams
                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_sail
                            ), "hungary"
                        ), 'A'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_sattelite
                            ), "Gibraltar"
                        ), 'A'
                    )
                )


                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_save
                            ), "Guyana"
                        ), 'A'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_server
                            ), "Haiti"
                        ), 'A'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_server_stack
                            ), "Honduras"
                        ), 'B'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_stick_man
                            ), "Greenland"
                        ), 'B'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_time_start
                            ), "Iran"
                        ), 'B'
                    )
                )

                tournamentList[2].registeredTeams.add(
                    Tournament.RegisteredTeam(
                        Team(
                            Icon.createWithResource(
                                context, R.drawable.icon_toast
                            ), "Greece"
                        ), 'B'
                    )
                )

                for (registeredTeam in tournamentList[2].registeredTeams) {
                    registeredTeam.team.location = Location( registeredTeam.team.name + " stadium")
                }

                for (match in tournamentList[2].matches) {
                    match.location = match.team1.team.location!!
                }






                //endregion

                //endregion




                for (tournament in tournamentList) {
                    tournament.orderMatches()
                }

                return tournamentList
            }
*/

            fun locationList(context: Context) : ArrayList<Location> {

                var locations = ArrayList<Location>()

                locations.add(Location("Stadium of Liberty"))
                locations.add(Location("Eiffel Stadium"))
                locations.add(Location("International Stadium of the Android Developers"))
                locations.add(Location("Googleplex"))
                locations.add(Location("Stadium of love affairs"))
                locations.add(Location("Stadium of laughter"))

                return locations

            }


            /*
            fun teamList(context: Context): ArrayList<Team> {
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
*/

            fun iconList(context: Context) : ArrayList<Icon> {

                var iconList = ArrayList<Icon>()

                iconList.add(Icon.createWithResource( context,
                    R.drawable.drunk_fighters
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.apple
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.spotify
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.android
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.microsoft_windows
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.paypal
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.youtube
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_airplane
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_battery
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_bed
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_building
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_case
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_center
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_climb
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_clip
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_compass
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_devices
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_drinks
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_eye
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_eye_glasses
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_glasses
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_helicopter
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_hot
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_kiss
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_light
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_lightning
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_money
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_nature_person
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_pizza
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_reading
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_sail
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_sattelite
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_save
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_server
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_server_stack
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_stick_man
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_time_start
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_toast
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_trash
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_university
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_wave
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_wifi
                ))
                iconList.add(Icon.createWithResource( context,
                    R.drawable.icon_world
                ))

                return iconList
            }
        }

    }

}


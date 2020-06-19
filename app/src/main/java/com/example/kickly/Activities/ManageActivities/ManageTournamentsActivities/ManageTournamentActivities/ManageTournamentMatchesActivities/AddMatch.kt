package com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.locationList
import com.example.kickly.Classes.Kickly.Companion.selectCode
import com.example.kickly.Classes.Kickly.Companion.selectTeam1Code
import com.example.kickly.Classes.Kickly.Companion.selectTeam2Code
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.Classes.Location
import com.example.kickly.Match
import com.example.kickly.R
import com.example.kickly.Team
import com.example.kickly.Tournament
import kotlinx.android.synthetic.main.activity_add_match.*
import kotlinx.android.synthetic.main.activity_add_match.spinner_location
import kotlinx.android.synthetic.main.activity_manage_team.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AddMatch : AppCompatActivity(), AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var team1ID : Int? = null
    var team2ID : Int? = null
    var locationID: Int? = null

    var location : Location? = null

    var homeLocation : Int? = null

    var team1 : Team? = null
    var team2 : Team? = null

    var registeredTeam1 : Tournament.RegisteredTeam? = null
    var registeredTeam2 : Tournament.RegisteredTeam? = null

    var teams = ArrayList<Tournament.RegisteredTeam>()

    var dateTime : LocalDateTime? = null

    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_match)

        title = getString(R.string.add_match)

        var tournamentID = intent.extras!!.getInt("tournamentID")
        var tournament = tournamentList[tournamentID]

        var group = intent.extras!!.getString("group")!!
        tv_group.text = getString(R.string.group_x, group)

        teams = tournament.group(group.first())

        var teamSelect = Intent(this, AddMatchSelectTeam::class.java)
        teamSelect.putExtra("group", group)

        team1ID?.let {
            teamSelect.putExtra("team1ID", it)
        }

        team2ID?.let {
            teamSelect.putExtra("team2ID", it)
        }

        team1Select.setOnClickListener {
            teamSelect.putExtra("requestCode", selectTeam1Code)
            startActivityForResult(teamSelect, selectTeam1Code)
        }

        team2Select.setOnClickListener {
            teamSelect.putExtra("requestCode", selectTeam2Code)
            startActivityForResult(teamSelect, selectTeam2Code)
        }

        var locationNames = ArrayList<String>()

        for (location in Kickly.locationList) {
            locationNames.add(location.name)
        }

        spinner_location.adapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item_text,
            R.id.text_view_location_name,
            locationNames
        )

        spinner_location.onItemSelectedListener = this

        spinner_location.setSelection(0)
        locationID = 0

        button_finish.setOnClickListener {

            if (team1ID == null || team2ID == null) {

                Toast.makeText(this, "please select both teams", Toast.LENGTH_SHORT).show()

            } else if (dateTime == null) {

                Toast.makeText(this, "please select date and time", Toast.LENGTH_SHORT).show()


            } else {


                registeredTeam1 = teams[team1ID!!]

                registeredTeam2 = teams[team2ID!!]

                location = locationList[locationID!!]

                tournament.matches.add(
                    Match(
                        registeredTeam1!!, registeredTeam2!!, dateTime!!, location!!
                    )
                )

                tournament.orderMatches()

                finish()

            }

        }

        button_dateAndTime.setOnClickListener {

            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month,day)
            datePickerDialog.show()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            it.extras?.let {
                it.getInt("teamID")?.let {
                    if (requestCode == selectTeam1Code) {

                        team1ID = data!!.extras!!.getInt("teamID")

                        var lol = "lol"
                        lol = "ahah"

                        if (team1ID == team2ID) {
                            Toast.makeText(
                                this,
                                getString(R.string.same_team_select),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            setTeam(1)
                        }

                    } else if (requestCode == selectTeam2Code) {

                        team2ID = data!!.extras!!.getInt("teamID")

                        if (team1ID == team2ID) {
                            Toast.makeText(
                                this,
                                getString(R.string.same_team_select),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            setTeam(2)
                        }

                    }
                }
            }
        }
    }

    fun setTeam(teamNo : Int) {
        var team : Tournament.RegisteredTeam? = null
        if (teamNo == 1) {
            team = teams[team1ID!!]

            imgTeam1Icon.setImageIcon(team!!.team.icon)
            tvTeam1Name.text = team.team.name

            if (locationID == null) {

                for (i in 0 until locationList.size ) {
                    if (locationList[i] == team.team.location) { locationID = i }
                }

                spinner_location.setSelection(locationID!!)

            }

        } else if (teamNo == 2) {
            team = teams[team2ID!!]

            imgTeam2Icon.setImageIcon(team!!.team.icon)
            tvTeam2Name.text = team.team.name

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        locationID = position
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day + 1
        myYear = year
        myMonth = month + 1
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute,
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        dateTime = LocalDateTime.of(myYear, myMonth, myDay, myHour, myMinute)
        date.text = dateTime!!.format(DateTimeFormatter.ISO_DATE)
        time.text = dateTime!!.format(DateTimeFormatter.ISO_TIME)
    }

}
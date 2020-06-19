package com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities.ManageTournamentActivities.ManageTournamentMatchesActivities

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kickly.Classes.Kickly.Companion.checkData
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.R
import com.example.kickly.Stats
import kotlinx.android.synthetic.main.activity_match_finish.*
import kotlinx.android.synthetic.main.activity_match_finish.imgTeam1Icon
import kotlinx.android.synthetic.main.activity_match_finish.imgTeam2Icon
import kotlinx.android.synthetic.main.activity_match_finish.tvTeam1Name
import kotlinx.android.synthetic.main.activity_match_finish.tvTeam2Name
import kotlinx.android.synthetic.main.activity_match_stats.*


class MatchFinish : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_finish)

        checkData()

        title = getString(R.string.finish_match)


        var tournamentID = intent.extras!!.getInt("tournamentID")
        var matchID = intent.extras!!.getInt("matchID")

        var tournament = tournamentList[tournamentID]
        var match = tournament.matches[matchID]

        imgTeam1Icon.setImageIcon(match.team1.team.icon)
        imgTeam2Icon.setImageIcon(match.team2.team.icon)
        tvTeam1Name.text = match.team1.team.name
        tvTeam2Name.text = match.team2.team.name

        button.setOnClickListener {

            if (
                editText_Team1AttemptsOnTarget.text.toString() == "" ||
                editText_Team2AttemptsOnTarget.text.toString() == "" ||
                editText_Team2AttemptsOnTarget.text.toString() == "" ||
                editText_Team2AttemptsOnTarget.text.toString() == "" ||

                editText_Team2FoulsCommited.text.toString() == "" ||
                editText_Team2FoulsCommited.text.toString() == "" ||

                editText_Team2YellowCards.text.toString() == "" ||
                editText_Team2YellowCards.text.toString() == "" ||

                editText_Team2RedCards.text.toString() == "" ||
                editText_Team2RedCards.text.toString() == "" ||

                editText_Team2Offsides.text.toString() == "" ||
                editText_Team2Offsides.text.toString() == "" ||

                editText_Team2Corners.text.toString() == "" ||
                editText_Team2Corners.text.toString() == "" ||

                editText_Team1Possession.text.toString() == ""

            ) {

                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()

            } else {

                match.finish( editText_Team1Score.text.toString().toInt(), editText_Team2Score.text.toString().toInt(), Stats(

                    editText_Team1AttemptsOnTarget.text.toString().toInt(),
                    editText_Team2AttemptsOnTarget.text.toString().toInt(),

                    editText_Team2AttemptsOnTarget.text.toString().toInt(),
                    editText_Team2AttemptsOnTarget.text.toString().toInt(),

                    editText_Team2FoulsCommited.text.toString().toInt(),
                    editText_Team2FoulsCommited.text.toString().toInt(),

                    editText_Team2YellowCards.text.toString().toInt(),
                    editText_Team2YellowCards.text.toString().toInt(),

                    editText_Team2RedCards.text.toString().toInt(),
                    editText_Team2RedCards.text.toString().toInt(),

                    editText_Team2Offsides.text.toString().toInt(),
                    editText_Team2Offsides.text.toString().toInt(),

                    editText_Team2Corners.text.toString().toInt(),
                    editText_Team2Corners.text.toString().toInt(),

                    editText_Team1Possession.text.toString().toInt()

                )
                )

                finish()
            }
        }

        // code from https://stackoverflow.com/questions/8063439/android-edittext-finished-typing-event
        (editText_Team1Possession as EditText).setOnEditorActionListener(
            OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action === KeyEvent.ACTION_DOWN && event.keyCode === KeyEvent.KEYCODE_ENTER
                ) {
                    if (event == null || !event.isShiftPressed) {

                        // the user is done typing.
                        textView_Team2Possession.text = (100 - editText_Team1Possession.text.toString().toInt()).toString()

                        return@OnEditorActionListener true // consume.
                    }
                }
                false // pass on to other listeners.
            }
        )


    }
}
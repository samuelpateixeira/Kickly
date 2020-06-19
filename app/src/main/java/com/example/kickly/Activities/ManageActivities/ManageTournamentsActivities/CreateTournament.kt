package com.example.kickly.Activities.ManageActivities.ManageTournamentsActivities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kickly.Activities.ManageActivities.TeamImagePicker
import com.example.kickly.Classes.Kickly
import com.example.kickly.Classes.Kickly.Companion.createCode
import com.example.kickly.Classes.Kickly.Companion.iconList
import com.example.kickly.Classes.Kickly.Companion.selectCode
import com.example.kickly.Classes.Kickly.Companion.tournamentList
import com.example.kickly.R
import com.example.kickly.Team
import com.example.kickly.Tournament
import kotlinx.android.synthetic.main.activity_create_tournament.*
import kotlinx.android.synthetic.main.activity_manage_team.*
import kotlinx.android.synthetic.main.activity_manage_team.button

class CreateTournament : AppCompatActivity() {

    var iconID : Int? = null

    var tournamentName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tournament)

        title = getString(R.string.create_tournament)

        image.setImageIcon(Icon.createWithResource(this, R.drawable.image))

        var selectIconIntent = Intent(this, TeamImagePicker::class.java)
        selectIconIntent.putExtra("requestCode", selectCode)

        image.setOnClickListener { startActivityForResult(selectIconIntent, selectCode) }

        button.setOnClickListener {

            if ( edit_text_tournament_name.text.toString() == "" ) {
                Toast.makeText(this, getString(R.string.please_choose_a_tournament_name), Toast.LENGTH_SHORT).show()
            } else if ( iconID == null ) {
                Toast.makeText(this, getString(R.string.please_choose_an_icon), Toast.LENGTH_SHORT).show()
            } else {

                tournamentName = edit_text_tournament_name.text.toString()

                    tournamentList.add(Tournament(iconList[iconID!!], tournamentName!!))

                setResult(Activity.RESULT_OK)
                finish()

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        iconID = data!!.extras!!.getInt("iconID")

        image.setImageIcon(iconList[iconID!!])

    }

}
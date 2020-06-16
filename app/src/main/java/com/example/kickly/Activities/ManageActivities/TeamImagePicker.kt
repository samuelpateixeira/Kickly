package com.example.kickly.Activities.ManageActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kickly.Classes.Kickly.Companion.iconList
import com.example.kickly.KicklyTools
import com.example.kickly.R
import kotlinx.android.synthetic.main.activity_matches.*
import kotlinx.android.synthetic.main.activity_team_image_picker.*
import kotlinx.android.synthetic.main.activity_team_image_picker.recyclerView

class TeamImagePicker : AppCompatActivity() {

    var selectCode = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_image_picker)

        if (intent.extras!!.getInt("requestCode") == selectCode) {

            recyclerView.adapter = KicklyTools.Adapters.IconPicker(this, iconList)
            recyclerView.layoutManager = GridLayoutManager(this, 4)

        }
    }
}
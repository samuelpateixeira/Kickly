package com.example.kickly

import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.util.*
import kotlin.collections.ArrayList

class MainMenuActivity : AppCompatActivity() {

    //create a list of buttons (IconTextIntent)
    var buttonList = ArrayList<IconTextActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        //region add the buttons to the list

        // Watching
        buttonList.add(IconTextActivity(
            Icon.createWithResource(
            this, R.drawable.icon_eye_white), getResources().getString(R.string.watching).capitalize(),
            Intent(this, WatchingActivity::class.java)
        ))

        // Profile
        buttonList.add(IconTextActivity(Icon.createWithResource(
            this, R.drawable.icon_profile_black), getResources().getString(R.string.profile).capitalize(),
            Intent(this, ProfileActivity::class.java)))

        // Manage
        buttonList.add(IconTextActivity(Icon.createWithResource(
            this, R.drawable.icon_plus_circle_gray), getResources().getString(R.string.manage).capitalize(),
            Intent(this, ManageActivity::class.java)))

        //endregion

        //set the adapter
        lvButtons.adapter = KicklyTools.Adapters.IconTextActivity(this, buttonList)



    }



}

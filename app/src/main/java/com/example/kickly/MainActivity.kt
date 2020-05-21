package com.example.kickly

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_list_item.view.*

class MainActivity : AppCompatActivity() {

    var buttonList = ArrayList<IconTextIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonList.add(IconTextIntent(Icon.createWithResource(
            this, R.drawable.icon_eye_white), getResources().getString(R.string.watching),
            Intent(this, Watching::class.java)))

        buttonList.add(IconTextIntent(Icon.createWithResource(
            this, R.drawable.icon_profile_black), getResources().getString(R.string.profile),
            Intent(this, Profile::class.java)))

        buttonList.add(IconTextIntent(Icon.createWithResource(
            this, R.drawable.icon_plus_circle_gray), getResources().getString(R.string.manage),
            Intent(this, Manage::class.java)))

        lvButtons.adapter = IconTextButtonListAdapter(this, buttonList)



    }



}

class IconTextButtonListAdapter(context: Context, objects : ArrayList<IconTextIntent>) : ArrayAdapter<IconTextIntent>(context, R.layout.activity_main_list_item, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //inflate the view
        var thisButton =LayoutInflater.from(context).inflate(R.layout.activity_main_list_item, parent, false)

        var currentIconTextActivity = getItem(position)!!

        thisButton.imgIcon.setImageIcon(currentIconTextActivity.icon)
        thisButton.tvText.text = currentIconTextActivity.text

        thisButton.setOnClickListener {
            context.startActivity(currentIconTextActivity.intent)
        }

        return thisButton

        }


}


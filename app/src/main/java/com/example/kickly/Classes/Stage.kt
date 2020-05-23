package com.example.kickly.Classes

import android.content.Context
import com.example.kickly.Activities.MainActivity
import com.example.kickly.KicklyTools
import com.example.kickly.R

enum class Stage {

    GROUPSTAGE, KNOCKOUTSTAGE;


    // override toString to use appropriate resource string
    override fun toString(): String {

        var string = ""

        when (this) {
            GROUPSTAGE -> string = MainActivity.context!!.resources.getString(R.string.group_stage)
            KNOCKOUTSTAGE -> string =  MainActivity.context!!.resources.getString(R.string.knockout_stage)
        }

        return string
    }

}


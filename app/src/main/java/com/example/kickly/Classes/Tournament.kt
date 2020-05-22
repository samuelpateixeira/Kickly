package com.example.kickly

import android.graphics.drawable.Icon

class Tournament(icon : Icon, name: String, currentStage : String, previousMatch : Match, nextMatch: Match  ) {

    var icon : Icon? = icon
    var name : String? = name
    var currentStage : String? = currentStage
    var previousMatch : Match? = previousMatch
    var nextMatch : Match? = nextMatch

}
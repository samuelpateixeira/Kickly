package com.example.kickly

import android.graphics.drawable.Icon
import com.example.kickly.Classes.Stage

class Tournament(icon : Icon, name: String, currentStage : Stage, previousMatch : Match, nextMatch: Match  ) {

    //region properties
    var icon : Icon? = icon
    var name : String? = name
    var currentStage : Stage? = currentStage
    var previousMatch : Match? = previousMatch
    var nextMatch : Match? = nextMatch
    //endregion

}
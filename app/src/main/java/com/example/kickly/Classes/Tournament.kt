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
    var registeredTeams = ArrayList<RegisteredTeam>()
    var matches = ArrayList<Match>()

    //endregion

    // registered team class
    class RegisteredTeam( var team : Team, var group : Char ) {
        var active : Boolean = true
        var matches = 0
        var points = 0
        var goalsScored = 0
        var goalsConceded = 0



        fun goalsDifference() : Int { return goalsScored - goalsConceded }


    }

    // registered team class
    class Group( var teams : ArrayList<RegisteredTeam>, var group : Char ) {
    }

    fun byGroup(): Map<Char, List<RegisteredTeam>> {return registeredTeams.groupBy { it.group  } }

    // get an array of groups
    fun groupsArray() : ArrayList<Group> {

        var byGroup = byGroup()

        // get the keys (groups)
        var keys = byGroup.keys
        var groups = ArrayList<Group>()

        // for each key
        for (key in keys) {

            var teams = ArrayList<RegisteredTeam>()

            // for each team of the key (group)
            for ( team in byGroup.get(key)!! ) {
                teams.add(team) // add it to the array
            }

            // add the array and key to a group
            groups.add( Group(teams, key) )
        }

        // i'm not sure what this is
        groups.sortBy { it.group }

        return groups

    }

}
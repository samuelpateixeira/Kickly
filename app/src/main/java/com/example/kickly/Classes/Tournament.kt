package com.example.kickly

import android.graphics.drawable.Icon
import com.example.kickly.Classes.Stage
import java.time.LocalDateTime

class Tournament(icon : Icon, name: String, currentStage : Stage ) {

    //region properties
    var icon : Icon? = icon
    var name : String? = name
    var currentStage : Stage? = currentStage
    //var nextMatch : Match? = nextMatch
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

    class Group( var teams : ArrayList<RegisteredTeam>, var group : Char ) {

    }

    fun byGroup(): Map<Char, List<RegisteredTeam>> {return registeredTeams.groupBy { it.group  } }

    fun previousMatches() : ArrayList<Match> {

        var previousMatches = ArrayList<Match>()

        for (match in matches) {
            if (match.isFinished) {
                previousMatches.add(match)
            }
        }

        return previousMatches
    }

    fun nextMatches() : ArrayList<Match> {

        var previousMatches = ArrayList<Match>()

        for (match in matches) {
            if (!match.isFinished) {
                previousMatches.add(match)
            }
        }

        return previousMatches
    }

    fun previousMatch() : Match {

        var previousMatch = previousMatches().last()

        for (match in previousMatches()) {

            if (KicklyTools.timeDifference(previousMatch.dateTime!! ,match.dateTime!!) > 0)
                previousMatch = match
        }

        return previousMatch

    }

    fun nextMatch() : Match {

        var nextMatch = nextMatches().last()

        for (match in nextMatches()) {

            if (KicklyTools.timeDifference(nextMatch.dateTime!! ,match.dateTime!!) < 0)
                nextMatch = match
        }

        return nextMatch

    }

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



    fun orderMatches() {


        /*
        var matchesPool = matches

        var orderedMatches = ArrayList<Match>()

        var timeDifferece = Long.MAX_VALUE

        while (matchesPool.size > 0) {

            var i = 0
            var pos = 0

            while (i < matchesPool.size) {

                if (KicklyTools.timeDifference( LocalDateTime.now(), matchesPool[i].dateTime ) < timeDifferece ) {
                    pos = i
                }

                i++

            }

            orderedMatches.add(matchesPool[pos])
            matchesPool.removeAt(pos)

            }

        matches = orderedMatches
        */


        var orderedMatches : ArrayList<Match> =
            matches.sortBy { KicklyTools.timeDifference( LocalDateTime.now(), it.dateTime ) } as ArrayList<Match>
        matches = orderedMatches

    }

}
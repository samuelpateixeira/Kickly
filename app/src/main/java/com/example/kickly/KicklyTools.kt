package com.example.kickly

import android.content.Context
import java.time.LocalDateTime
import java.time.ZoneOffset

class KicklyTools {

    companion object {

        // returns a string with the time left to the input date
        // example: in 5 days / in 3 hours
        fun timeLeft(futureTime : LocalDateTime, context: Context) : String {

            var timeLeftString : String?

            // get times and the times in EpochMilli (milliseconds since 1970)
            var currentTime = LocalDateTime.now()
            var futureTimeMilli = futureTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            var currentTimeMilli = currentTime.toInstant(ZoneOffset.UTC).toEpochMilli()
            var differenceMilli = futureTimeMilli - currentTimeMilli

            // get total amount of remaining seconds, minutes, hours and days (27 hours instead of 3 hours and 1 day)
            val totalSeconds = differenceMilli / 1000
            val totalMinutes = totalSeconds / 60
            val totalHours = totalMinutes / 60
            val totalDays = totalHours / 24

            // get remaining time (3 hours and one day instead of 27 hours)
            val seconds = totalSeconds - (totalMinutes * 60)
            val minutes = totalMinutes - (totalHours * 60)
            val hours = totalHours - (totalDays * 24)
            val days = totalDays

            // get and format appropriate string
            if (days > 0) {
                timeLeftString = context.resources.getString(R.string.daysLeft, days)
            } else if (hours > 0) {
                timeLeftString = context.resources.getString(R.string.hoursLeft, hours)
            } else if (minutes > 0) {
                timeLeftString = context.resources.getString(R.string.minutesLeft, minutes)
            } else {
                timeLeftString = context.resources.getString(R.string.now)
            }

            return timeLeftString

        }

    }
}
package com.abschlussProjekt.dotastats.util


const val res_url = "https://api.opendota.com"
fun getMatchDuration(seconds: Int): String {
    val hour = seconds / 3600
    val min = if (seconds / 60 > 60) (seconds / 60 - 60) else seconds / 60
    val sec = seconds % 60

    return when {
        hour < 1 -> "${addZeroToInt(min)}:${addZeroToInt(sec)}"
        else ->
            "$hour:${addZeroToInt(min)}:${addZeroToInt(sec)}"
    }
}

fun getFormattedValue(value: Int): String =
    if (value >= 1000)
        String.format("%.1f", value / 1000.0) + "k"
    else "$value"


fun addZeroToInt(number: Int): String = if (number < 10) "0$number" else "$number"

fun calcWinRate(stats: Map<String, Int>): String {
    val totalMatches = stats["win"]!! + stats["lose"]!!
    val winRate = ((stats["win"]!!.toDouble() / totalMatches) * 100)
    return "${String.format("%.2f", winRate)}%"
}

fun getDurationBetween(startTime: Long): String {


    val currentTime = System.currentTimeMillis()
    val duration = (currentTime - startTime * 1000)


    val sec = duration / 1000 % 60
    val min = (duration / (1000 * 60) % 60)
    val hour = duration / (1000 * 60 * 60) % 24
    val days = duration / (1000 * 60 * 60 * 24)
    val months = duration / (1000 * 60 * 60 * 24) / 30
    val years: Long = duration / (1000L * 60 * 60 * 24 * 30) / 12



    return when {
        years >= 1 -> {
            if (years == 1L) "a year ago" else "$years years ago"
        }

        months >= 1 -> {
            if (months == 1L) "a month ago" else "$months months ago"
        }

        days >= 1 -> {
            if (days == 1L) "a day ago" else "$days days ago"
        }

        hour >= 1 -> {
            if (hour == 1L) "a hour ago" else "$hour hours ago"
        }

        min >= 1 -> {
            if (min == 1L) "a min ago" else "$min min ago"
        }

        sec >= 1 -> {
            if (sec == 1L) "a sec ago" else "$sec seconds ago"
        }

        else -> {
            "error"
        }

    }
}
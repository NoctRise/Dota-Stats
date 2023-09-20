package com.abschlussProjekt.dotastats.util

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

fun addZeroToInt(number: Int): String = if (number < 10) "0$number" else "$number"
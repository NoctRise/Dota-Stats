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
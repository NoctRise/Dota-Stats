package com.abschlussProjekt.dotastats.data.datamodels

data class PlayerProfile(
    val account_id: Long,
    val personaname: String,
    val name: String?,
    val avatarfull: String,
    val last_login: String?
)
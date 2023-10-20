package com.abschlussProjekt.dotastats.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abschlussProjekt.dotastats.data.OpenDotaApi
import com.abschlussProjekt.dotastats.data.Repository
import com.abschlussProjekt.dotastats.data.database.getInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DotaViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getInstance(application)
    private val repository = Repository(OpenDotaApi.retrofitService, database)

    val recentProMatches = repository.recentProMatches

    val detailProMatch = repository.detailProMatch

    val proTeams = repository.proTeams

    val playerProfile = repository.playerProfile

    val playerWinLose = repository.playerWinLose

    val playerRecentMatches = repository.playerRecentMatches

    init {
        initDB()
    }

    private fun initDB() = viewModelScope.launch(Dispatchers.IO) { repository.initDB() }

    fun getRecentProMatches() {
        viewModelScope.launch(Dispatchers.IO) { repository.getRecentProMatches() }
    }

    fun getMatchById(matchID: Long) {
        viewModelScope.launch(Dispatchers.IO) { repository.getMatchById(matchID) }
    }

    fun getTeams() {
        viewModelScope.launch(Dispatchers.IO) { repository.getTeams() }
    }

    fun getPlayerProfileByID(accountID: Long) {
        viewModelScope.launch(Dispatchers.IO) { repository.getPlayerProfileByID(accountID) }
    }

    fun getPlayerWinLoseByID(accountID: Long) {
        viewModelScope.launch(Dispatchers.IO) { repository.getPlayerWinLoseByID(accountID) }
    }

    fun getPlayerRecentMatchesByID(accountID: Long) {
        viewModelScope.launch(Dispatchers.IO) { repository.getPlayerRecentMatchesByID(accountID) }
    }
}
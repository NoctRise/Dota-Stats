package com.abschlussProjekt.dotastats.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.abschlussProjekt.dotastats.data.OpenDotaApi
import com.abschlussProjekt.dotastats.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(OpenDotaApi.retrofitService)

    val recentProMatches = repository.recentProMatches

    fun getRecentProMatches() {
        viewModelScope.launch(Dispatchers.IO) { repository.getRecentProMatches() }
    }

    fun getMatchById() {
        viewModelScope.launch(Dispatchers.IO) { repository.getMatchById(7340499325) }
    }
}
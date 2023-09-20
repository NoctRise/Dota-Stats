package com.abschlussProjekt.dotastats.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abschlussProjekt.dotastats.data.datamodels.ProMatch

class Repository(private val apiService: OpenDotaApiService) {

    private val _recentProMatches = MutableLiveData<List<ProMatch>>()
    val recentProMatches: LiveData<List<ProMatch>>
        get() = _recentProMatches

    suspend fun getRecentProMatches() {
        _recentProMatches.postValue(apiService.getRecentProMatches())
    }
}
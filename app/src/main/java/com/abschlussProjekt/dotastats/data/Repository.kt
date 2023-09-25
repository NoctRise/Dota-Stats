package com.abschlussProjekt.dotastats.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abschlussProjekt.dotastats.data.datamodels.ProMatch

private const val TAG = "Repository"

class Repository(private val apiService: OpenDotaApiService) {

    private val _recentProMatches = MutableLiveData<List<ProMatch>>()
    val recentProMatches: LiveData<List<ProMatch>>
        get() = _recentProMatches

    suspend fun getRecentProMatches() {
        try {
            _recentProMatches.postValue(apiService.getRecentProMatches())
        } catch (ex: Exception) {
            Log.e("$TAG-getRecentProMatches", "Error loading data from api: $ex")
        }
    }

    suspend fun getMatchById(id: Long) {
        try {
            val matchDetail = apiService.getMatchById(id)
            matchDetail.players.forEach{
                Log.d("MatchById", it.toString())
            }

        } catch (ex: Exception) {
            Log.e("$TAG-getMatchById", "Error loading data from api: $ex")
        }
    }
}
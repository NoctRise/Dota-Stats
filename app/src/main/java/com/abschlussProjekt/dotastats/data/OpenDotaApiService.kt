package com.abschlussProjekt.dotastats.data

import com.abschlussProjekt.dotastats.data.datamodels.ProMatch
import com.abschlussProjekt.dotastats.data.datamodels.ProMatchDetailAPI
import com.abschlussProjekt.dotastats.data.datamodels.ProTeam
import com.abschlussProjekt.dotastats.data.datamodels.constants.Ability
import com.abschlussProjekt.dotastats.data.datamodels.constants.Hero
import com.abschlussProjekt.dotastats.data.datamodels.constants.Item
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


const val base_URL = "https://api.opendota.com/api/"

private val moshi =
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit =
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(base_URL)
        .build()


interface OpenDotaApiService {

    @GET("proMatches")
    suspend fun getRecentProMatches(): List<ProMatch>

    @GET("matches/{id}")
    suspend fun getMatchById(@Path("id") id: Long): ProMatchDetailAPI

    @GET("teams")
    suspend fun getTeams(): List<ProTeam>


    // Constants
    @GET("constants/items")
    suspend fun getItems(): Map<String, Item>

    @GET("constants/heroes")
    suspend fun getHeroes(): Map<String, Hero>

    @GET("constants/ability_ids")
    suspend fun getAbilityIDS(): Map<String, String>

    @GET("constants/abilities")
    suspend fun getAbilities(): Map<String, Ability>

}

object OpenDotaApi {
    val retrofitService: OpenDotaApiService by lazy { retrofit.create(OpenDotaApiService::class.java) }
}
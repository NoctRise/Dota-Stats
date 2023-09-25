package com.abschlussProjekt.dotastats.data

import com.abschlussProjekt.dotastats.data.datamodels.ProMatch
import com.abschlussProjekt.dotastats.data.datamodels.ProMatchDetail
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
    suspend fun getMatchById(@Path("id") id: Long): ProMatchDetail
}

object OpenDotaApi {
    val retrofitService: OpenDotaApiService by lazy { retrofit.create(OpenDotaApiService::class.java) }
}
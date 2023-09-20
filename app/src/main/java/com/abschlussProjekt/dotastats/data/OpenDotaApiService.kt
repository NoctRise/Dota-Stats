package com.abschlussProjekt.dotastats.data

import com.abschlussProjekt.dotastats.data.datamodels.ProMatch
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


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
}

object OpenDotaApi {
    val retrofitService: OpenDotaApiService by lazy { retrofit.create(OpenDotaApiService::class.java) }
}
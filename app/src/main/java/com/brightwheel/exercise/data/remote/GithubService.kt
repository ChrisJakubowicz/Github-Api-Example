package com.brightwheel.exercise.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val USER_AGENT = "ChrisJakubowicz"
private const val BASE_URL = "https://api.github.com/"

object GithubService {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api: GithubApi by lazy {
        retrofit.create(GithubApi::class.java)
    }
}

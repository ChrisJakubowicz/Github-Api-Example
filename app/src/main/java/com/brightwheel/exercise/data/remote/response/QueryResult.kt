package com.brightwheel.exercise.data.remote.response

import com.squareup.moshi.Json

data class QueryResult(
    @Json(name = "total_count") val total: Int = 0,
    val items: List<Repo>,
    val nextPage: Int? = null
)

data class Repo(
    val id: Long,
    val name: String,
    val owner: Owner,
)

data class Owner(val login: String)

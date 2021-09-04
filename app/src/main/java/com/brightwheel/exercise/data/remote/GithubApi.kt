package com.brightwheel.exercise.data.remote

import com.brightwheel.exercise.BuildConfig
import com.brightwheel.exercise.data.remote.response.Contributor
import com.brightwheel.exercise.data.remote.response.QueryResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    /**
     * Query search syntax is located in the github api documentation:
     * https://docs.github.com/en/github/searching-for-information-on-github/getting-started-with-searching-on-github/understanding-the-search-syntax
     */
    @Headers(
        "User-Agent: $USER_AGENT",
        "Authorization: token ${BuildConfig.GITHUB_API_KEY}"
    )
    @GET("search/repositories?q=stars:>0&sort=stars&order=desc")
    suspend fun getReposSortedByHighestStarCount(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): QueryResult

    @Headers(
        "User-Agent: $USER_AGENT",
        "Authorization: token ${BuildConfig.GITHUB_API_KEY}"
    )
    @GET("repos/{owner}/{repo}/contributors?per_page=1&order=desc")
    suspend fun getTopContributor(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): List<Contributor>
}

package com.brightwheel.exercise.ui.github

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brightwheel.exercise.data.remote.GithubService
import com.brightwheel.exercise.data.remote.response.Contributor
import com.brightwheel.exercise.data.remote.response.QueryResult
import com.brightwheel.exercise.data.remote.response.Repo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepoListViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val DEFAULT_UNKNOWN_CONTRIBUTOR = "Unknown"
    private val _reposUiModel = MutableLiveData<List<RepoItemUiModel>>()
    val reposUiModel: LiveData<List<RepoItemUiModel>> = _reposUiModel

    fun getReposSortedByHighestStarCount() {
        viewModelScope.launch {
            val fetchedRepoList = GithubService.api.getReposSortedByHighestStarCount(1, 100)
            val contributorMap = fetchItems(fetchedRepoList)
            getUiModel(fetchedRepoList.items, contributorMap)
        }
    }

    private fun getUiModel(repos: List<Repo>, contributors: Map<Long, Contributor>) {
        val repoItemUiModelList = mutableListOf<RepoItemUiModel>()
        repos.forEach { repo ->
            repoItemUiModelList.add(
                RepoItemUiModel(
                    repo.name,
                    contributors.getOrElse(repo.id) {
                        Contributor(DEFAULT_UNKNOWN_CONTRIBUTOR)
                    }.login
                )
            )
        }

        _reposUiModel.value = repoItemUiModelList.toList()
    }

    private suspend fun fetchItems(repos: QueryResult): Map<Long, Contributor> {
        val itemById = mutableMapOf<Long, Contributor>()
        coroutineScope {
            repos.items.forEach { repo ->
                launch {
                    try {
                        itemById[repo.id] = GithubService.api.getTopContributor(
                            repo.owner.login,
                            repo.name
                        )[0]
                    } catch (e: HttpException) {
                        itemById[repo.id] = Contributor(DEFAULT_UNKNOWN_CONTRIBUTOR)
                    }
                }
            }
        }
        return itemById
    }
}

data class RepoItemUiModel(val repoName: String, val highestContributor: String)

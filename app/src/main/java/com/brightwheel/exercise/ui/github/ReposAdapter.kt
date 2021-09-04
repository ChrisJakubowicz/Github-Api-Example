package com.brightwheel.exercise.ui.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brightwheel.exercise.R

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {
    private var repos: List<RepoItemUiModel> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView
        val topContributor: TextView

        init {
            repoName = view.findViewById(R.id.repo_name)
            topContributor = view.findViewById(R.id.repo_top_contributor)
        }
    }

    fun setRepos(uiModelRepos: List<RepoItemUiModel>) {
        repos = uiModelRepos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.repo_view_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.repoName.text = repos[position].repoName
        viewHolder.topContributor.text =
            repos[position].highestContributor + " is the top contributor"
    }

    override fun getItemCount() = repos.size
}

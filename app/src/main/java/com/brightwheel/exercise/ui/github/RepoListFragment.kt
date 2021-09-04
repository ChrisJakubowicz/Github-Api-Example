package com.brightwheel.exercise.ui.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brightwheel.exercise.R
import com.brightwheel.exercise.databinding.RepoListFragmentBinding

class RepoListFragment : Fragment(R.layout.repo_list_fragment) {

    private var _binding: RepoListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RepoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RepoListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addRecyclerViewDividers()

        viewModel.reposUiModel.observe(viewLifecycleOwner) { item ->
            val adapter = ReposAdapter()
            binding.list.adapter = adapter
            viewModel.reposUiModel.value?.let { adapter.setRepos(it) }

            if (viewModel.reposUiModel.value?.size == 100) {
                binding.indeterminateBar.visibility = View.GONE
            }
        }

        viewModel.getReposSortedByHighestStarCount()
    }

    private fun addRecyclerViewDividers() {
        DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            binding.list.addItemDecoration(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

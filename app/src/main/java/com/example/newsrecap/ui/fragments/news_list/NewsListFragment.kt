package com.example.newsrecap.ui.fragments.news_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsrecap.databinding.FragmentNewsListBinding
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.ui.adapters.NewsListAdapter
import com.example.newsrecap.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = NewsListAdapter(onNewsClicked = {
            viewModel.setSelectedNews(it)
            findNavController().navigate(NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment())
        })

        binding.rvNewsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNewsList.adapter = adapter

        binding.swipeRefreshLayoutNews.setOnRefreshListener {
            viewModel.getNews()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .distinctUntilChanged { old, new ->
                        old.isLoading == new.isLoading
                    }
                    .collect { uiState ->
                        binding.swipeRefreshLayoutNews.isRefreshing = uiState.isLoading
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    submitListAdapterAndScrollUp(adapter, uiState.newsList)
                    checkErrorStatusAndShowMessage(uiState.errorMessage)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun submitListAdapterAndScrollUp(adapter: NewsListAdapter, newsList: List<News>) {
        adapter.submitList(newsList) {
            binding.rvNewsList.scrollToPosition(0)
        }
    }

    private fun checkErrorStatusAndShowMessage(errorMessage: String?) {
        if (errorMessage != null) {
            Toast.makeText(
                context,
                "$errorMessage",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
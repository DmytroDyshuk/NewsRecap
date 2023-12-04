package com.example.newsrecap.ui.fragments.news_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsrecap.R
import com.example.newsrecap.databinding.FragmentNewsListBinding
import com.example.newsrecap.ui.adapters.NewsListAdapter
import com.example.newsrecap.ui.viewmodel.NewsViewModel
import com.example.newsrecap.utils.constants.SourcesConstants
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(activity, NewsViewModel.Factory(activity.application))[NewsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBarLayout()
        setupCLickListeners()

        val adapter = NewsListAdapter(onNewsClicked = {
            viewModel.setSelectedNews(it)
            NavHostFragment.findNavController(this).navigate(R.id.action_newsListFragment_to_newsDetailsFragment)
        })

        binding.rvNewsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNewsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .map { it.source }
                    .distinctUntilChanged()
                    .collect { source ->
                        if (source.isNotBlank()) {
                            viewModel.getNewsListBySource(source)
                        }
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    adapter.submitList(uiState.newsList)
                    binding.toolbar.title = uiState.newsTitle
                    binding.ivLoadingAnimation.isVisible = uiState.isLoading
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCLickListeners() {
        binding.ivMenu.setOnClickListener {
            binding.drawerLayout.open()
        }
    }

    private fun setAppBarLayout() {
        binding.nvNews.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_all_news -> {
                    setupAllNews()
                    true
                }

                R.id.item_wsj -> {
                    setupNewsSource(SourcesConstants.THE_WALL_STREET_JOURNAL, getString(R.string.wsj_news))
                    true
                }

                R.id.item_washington_post -> {
                    setupNewsSource(SourcesConstants.THE_WASHINGTON_POST, getString(R.string.the_washington_post))
                    true
                }

                R.id.item_time -> {
                    setupNewsSource(SourcesConstants.TIME, getString(R.string.time))
                    true
                }

                R.id.item_bbc -> {
                    setupNewsSource(SourcesConstants.BBC_NEWS, getString(R.string.bbc_news))
                    true
                }

                R.id.item_abc_news -> {
                    setupNewsSource(SourcesConstants.ABC_NEWS, getString(R.string.abc_news))
                    true
                }

                R.id.item_cnn_news -> {
                    setupNewsSource(SourcesConstants.CNN_NEWS, getString(R.string.cnn_news))
                    true
                }

                R.id.item_fox_news -> {
                    setupNewsSource(SourcesConstants.FOX_NEWS, getString(R.string.fox_news))
                    true
                }

                R.id.item_ign_news -> {
                    setupNewsSource(SourcesConstants.IGN, getString(R.string.ign))
                    true
                }

                R.id.item_national_geographic -> {
                    setupNewsSource(SourcesConstants.NATIONAL_GEOGRAPHIC, getString(R.string.national_geographic))
                    true
                }

                else -> false
            }
        }
    }

    private fun setupAllNews() {
        viewModel.refreshNews()
        viewModel.setNewsTitle(getString(R.string.all_news))
        binding.drawerLayout.close()
    }

    private fun setupNewsSource(source: String, newsTitle: String) {
        viewModel.setNewSource(source)
        viewModel.setNewsTitle(newsTitle)
        binding.drawerLayout.close()
    }

}
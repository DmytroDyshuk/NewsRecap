package com.example.newsrecap.ui.fragments.news_list.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsrecap.R
import com.example.newsrecap.databinding.FragmentNewsListBinding
import com.example.newsrecap.ui.fragments.news_list.adapter.NewsListAdapter
import com.example.newsrecap.ui.viewmodel.NewsViewModel
import com.example.newsrecap.utils.constants.SourcesConstants

class NewsListFragment: Fragment() {

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

        binding.ivLoadingAnimation.visibility = View.VISIBLE

        val adapter = NewsListAdapter(onNewsClicked = {
            viewModel.setSelectedNews(it)
            NavHostFragment.findNavController(this).navigate(R.id.action_newsListFragment_to_newsDetailsFragment)
        })

        binding.rvNewsList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNewsList.adapter = adapter

        viewModel.source.observe(viewLifecycleOwner) {
            viewModel.getNewsList()
        }

        viewModel.newsList.observe(viewLifecycleOwner) { newsList ->
            adapter.submitList(newsList)
            binding.ivLoadingAnimation.visibility = View.INVISIBLE
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
            when(it.itemId) {
                R.id.item_wsj -> {
                    viewModel.setNewSource(SourcesConstants.THE_WALL_STREET_JOURNAL)
                    binding.toolbar.title = getString(R.string.wsj_news)
                    //supportActionBar?.title = getString(R.string.wsj_news)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_washington_post -> {
                    viewModel.setNewSource(SourcesConstants.THE_WASHINGTON_POST)
                    binding.toolbar.title = getString(R.string.the_washington_post)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_time -> {
                    viewModel.setNewSource(SourcesConstants.TIME)
                    binding.toolbar.title = getString(R.string.time)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_bbc -> {
                    viewModel.setNewSource(SourcesConstants.BBC_NEWS)
                    binding.toolbar.title = getString(R.string.bbc_news)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_abc_news -> {
                    viewModel.setNewSource(SourcesConstants.ABC_NEWS)
                    binding.toolbar.title = getString(R.string.abc_news)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_cnn_news -> {
                    viewModel.setNewSource(SourcesConstants.CNN_NEWS)
                    binding.toolbar.title = getString(R.string.cnn_news)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_fox_news -> {
                    viewModel.setNewSource(SourcesConstants.FOX_NEWS)
                    binding.toolbar.title = getString(R.string.fox_news)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_ign_news -> {
                    viewModel.setNewSource(SourcesConstants.IGN)
                    binding.toolbar.title = getString(R.string.ign)
                    binding.drawerLayout.close()
                    true
                }
                R.id.item_national_geographic -> {
                    viewModel.setNewSource(SourcesConstants.NATIONAL_GEOGRAPHIC)
                    binding.toolbar.title = getString(R.string.national_geographic)
                    binding.drawerLayout.close()
                    true
                }
                else -> false
            }
        }
    }

}
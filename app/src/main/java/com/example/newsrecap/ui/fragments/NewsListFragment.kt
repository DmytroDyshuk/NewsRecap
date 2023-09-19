package com.example.newsrecap.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsrecap.databinding.FragmentNewsListBinding
import com.example.newsrecap.ui.adapter.NewsListAdapter
import com.example.newsrecap.ui.viewmodel.NewsViewModel

class NewsListFragment: Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, NewsViewModel.Factory(activity.application))[NewsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivLoadingAnimation.visibility = View.VISIBLE

        val adapter = NewsListAdapter()
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

}
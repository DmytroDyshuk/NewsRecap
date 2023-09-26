package com.example.newsrecap.ui.fragments.news_details.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsrecap.R
import com.example.newsrecap.databinding.FragmentNewsDetailsBinding
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.ui.viewmodel.NewsViewModel
import com.example.newsrecap.utils.parseDate

class NewsDetailsFragment : Fragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(activity, NewsViewModel.Factory(activity.application))[NewsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()

        viewModel.selectedNews.observe(viewLifecycleOwner) { news ->
            setSelectedNews(news)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.ivBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setSelectedNews(news: News) {
        setNewsImage(news.urlToImage)
        binding.tvNewsTitle.text = news.title
        binding.tvNewsSource.text = news.source?.name
        binding.tvNewsPublisher.text = "Author: ${news.author}"
        binding.tvNewsText.text = news.content
        binding.tvNewsTime.text = news.publishedAt?.parseDate()
    }

    private fun setNewsImage(url: String?) {
        Glide.with(binding.ivNewsImage)
            .load(url)
            .error(R.drawable.vector_broken_image)
            .into(binding.ivNewsImage)
    }

}
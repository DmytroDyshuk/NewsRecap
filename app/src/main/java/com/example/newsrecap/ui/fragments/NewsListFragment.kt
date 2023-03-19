package com.example.newsrecap.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsrecap.databinding.FragmentNewsListBinding
import com.example.newsrecap.model.News
import com.example.newsrecap.ui.adapter.NewsListAdapter

class NewsListFragment: Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testNewsList = mutableListOf(
            News(1,"", " ", "", "", "", "", ""),
            News(2,"", " ", "", "", "", "", ""),
            News(3, "", " ", "", "", "", "", ""),
            News(4, "", " ", "", "", "", "", ""),
            News(5, "", " ", "", "", "", "", "")
        )
        val adapter = NewsListAdapter()
        binding.rvNewsList.adapter = adapter
        binding.rvNewsList.layoutManager =LinearLayoutManager(this.context)
        adapter.submitList(testNewsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
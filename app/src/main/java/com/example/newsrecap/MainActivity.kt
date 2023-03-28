package com.example.newsrecap

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newsrecap.databinding.ActivityMainBinding
import com.example.newsrecap.ui.viewmodel.NewsViewModel
import com.example.newsrecap.utils.SourcesConstants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.ivMenu.setOnClickListener {
            binding.drawerLyout.open()
        }

        binding.nvNews.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.item_wsj -> {
                    viewModel.setNewSource(SourcesConstants.THE_WALL_STREET_JOURNAL)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_washington_post -> {
                    viewModel.setNewSource(SourcesConstants.THE_WASHINGTON_POST)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_time -> {
                    viewModel.setNewSource(SourcesConstants.TIME)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_bbc -> {
                    viewModel.setNewSource(SourcesConstants.BBC_NEWS)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_abc_news -> {
                    viewModel.setNewSource(SourcesConstants.ABC_NEWS)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_cnn_news -> {
                    viewModel.setNewSource(SourcesConstants.CNN_NEWS)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_fox_news -> {
                    viewModel.setNewSource(SourcesConstants.FOX_NEWS)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_ign_news -> {
                    viewModel.setNewSource(SourcesConstants.IGN)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_national_geographic -> {
                    viewModel.setNewSource(SourcesConstants.NATIONAL_GEOGRAPHIC)
                    binding.drawerLyout.close()
                    true
                }
                else -> false
            }
        }
    }
}
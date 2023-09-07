package com.example.newsrecap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.newsrecap.databinding.ActivityMainBinding
import com.example.newsrecap.ui.viewmodel.NewsViewModel
import com.example.newsrecap.utils.SourcesConstants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        viewModel.getEverythingNewsList()

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (viewModel.newsList.value != null) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )

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
                    supportActionBar?.title = getString(R.string.wsj_news)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_washington_post -> {
                    viewModel.setNewSource(SourcesConstants.THE_WASHINGTON_POST)
                    supportActionBar?.title = getString(R.string.the_washington_post)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_time -> {
                    viewModel.setNewSource(SourcesConstants.TIME)
                    supportActionBar?.title = getString(R.string.time)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_bbc -> {
                    viewModel.setNewSource(SourcesConstants.BBC_NEWS)
                    supportActionBar?.title = getString(R.string.bbc_news)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_abc_news -> {
                    viewModel.setNewSource(SourcesConstants.ABC_NEWS)
                    supportActionBar?.title = getString(R.string.abc_news)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_cnn_news -> {
                    viewModel.setNewSource(SourcesConstants.CNN_NEWS)
                    supportActionBar?.title = getString(R.string.cnn_news)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_fox_news -> {
                    viewModel.setNewSource(SourcesConstants.FOX_NEWS)
                    supportActionBar?.title = getString(R.string.fox_news)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_ign_news -> {
                    viewModel.setNewSource(SourcesConstants.IGN)
                    supportActionBar?.title = getString(R.string.ign)
                    binding.drawerLyout.close()
                    true
                }
                R.id.item_national_geographic -> {
                    viewModel.setNewSource(SourcesConstants.NATIONAL_GEOGRAPHIC)
                    supportActionBar?.title = getString(R.string.national_geographic)
                    binding.drawerLyout.close()
                    true
                }
                else -> false
            }
        }
    }

    private fun setOnPreDrawListener() {

    }
}
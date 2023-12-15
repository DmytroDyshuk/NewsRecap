package com.example.newsrecap.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.example.newsrecap.NewsRecapApp
import com.example.newsrecap.R
import com.example.newsrecap.data.repository.NetworkStatusRepository
import com.example.newsrecap.databinding.ActivityMainBinding
import com.example.newsrecap.ui.viewmodel.NewsViewModel
import com.example.newsrecap.utils.constants.SourcesConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: NewsViewModel by viewModels { NewsViewModel.Factory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationDrawerMenu()
        setupToolbar()
        initNetworkStatusObserver()
    }

    @OptIn(FlowPreview::class)
    private fun initNetworkStatusObserver() {
        val networkStatusRepository = NetworkStatusRepository(
            coroutineScope = CoroutineScope(Dispatchers.Default),
            connectivityObserver = (application as NewsRecapApp).networkConnectivityObserver
        )
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                networkStatusRepository.connectionStatus
                    .debounce(500)
                    .collect { status ->
                        binding.viewConnectionStatus.updateViewWithConnectStatus(status)
                    }
            }
        }
    }

    private fun setupNavigationDrawerMenu() {
        binding.nvNews.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_all_news -> {
                    setupNewsSource(SourcesConstants.ALL_NEWS, getString(R.string.all_news))
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

    private fun setupToolbar() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.newsDetailsFragment -> {
                    binding.toolbar.title = ""
                    binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.vector_arrow_back_icon)
                    binding.toolbar.setNavigationOnClickListener {
                        navHostFragment.navController.popBackStack()
                    }
                }

                else -> {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.uiState.collect {
                                binding.toolbar.title = it.newsTitle
                            }
                        }
                    }
                    binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.vector_menu_icon)
                    binding.toolbar.setNavigationOnClickListener {
                        binding.drawerLayout.open()
                    }
                }
            }
        }
    }

    private fun setupNewsSource(source: String, newsTitle: String) {
        viewModel.setNewsTitle(newsTitle)
        viewModel.setCurrentSource(source)
        viewModel.getNewsListBySource()
        binding.drawerLayout.close()
    }

}
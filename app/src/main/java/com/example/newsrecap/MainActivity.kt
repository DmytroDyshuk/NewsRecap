package com.example.newsrecap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsrecap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.ivMenu.setOnClickListener {
            binding.drawerLyout.open()
        }
    }
}
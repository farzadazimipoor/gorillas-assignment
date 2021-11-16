package io.gorillas.assignment.presentation.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import io.gorillas.assignment.R
import io.gorillas.assignment.databinding.ActivityMainBinding
import io.gorillas.assignment.presentation.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
    }
}
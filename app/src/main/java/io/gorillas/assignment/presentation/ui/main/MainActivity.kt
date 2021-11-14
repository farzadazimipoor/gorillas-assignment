package io.gorillas.assignment.presentation.ui.main

import android.os.Bundle
import io.gorillas.assignment.R
import io.gorillas.assignment.presentation.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
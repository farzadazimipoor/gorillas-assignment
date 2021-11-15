package io.gorillas.assignment.presentation.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.gorillas.assignment.R
import io.gorillas.assignment.common.enums.LoadingStatus
import io.gorillas.assignment.presentation.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        initGetPostObserver()

        getPost("1")
    }

    private fun initGetPostObserver() {
        viewModel.postResult.observe(this, {
            if (it.status == LoadingStatus.SUCCESS) {
                Toast.makeText(this, it.data?.title ?: "Empty", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getPost(id: String) {
        viewModel.getPost(id)
    }
}
package io.gorillas.assignment.presentation.ui.main.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.gorillas.assignment.R
import io.gorillas.assignment.common.enums.LoadingStatus
import io.gorillas.assignment.databinding.FragmentPostDetailBinding
import io.gorillas.assignment.di.Injectable
import io.gorillas.assignment.presentation.common.binding.FragmentDataBindingComponent
import io.gorillas.assignment.presentation.ui.main.MainActivity
import javax.inject.Inject

class PostDetailFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var viewModel: PostDetailViewModel

    private lateinit var binding: FragmentPostDetailBinding

    private var postId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postId = arguments?.getString("postId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_post_detail,
            container,
            false,
            dataBindingComponent
        )
        val toolbar = binding.toolbar
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostDetailViewModel::class.java)

        initPostDetailsObserver()

        postId?.let {
            fetchPostDetails(it)
        }
    }

    private fun initPostDetailsObserver() {
        viewModel.postResult.observe(this, {
            binding.resource = it
            if (it.status == LoadingStatus.SUCCESS) {
                binding.model = it.data
            }
        })
    }

    private fun fetchPostDetails(id: String) {
        viewModel.getPost(id)
    }
}
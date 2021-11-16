package io.gorillas.assignment.presentation.ui.main.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.gorillas.assignment.R
import io.gorillas.assignment.databinding.FargmentPostsBinding
import io.gorillas.assignment.di.Injectable
import io.gorillas.assignment.presentation.common.binding.FragmentDataBindingComponent
import javax.inject.Inject

class PostsFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var viewModel: PostsViewModel

    private lateinit var binding: FargmentPostsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fargment_posts,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[PostsViewModel::class.java]

        binding.button.setOnClickListener {
            gotoPostDetails("ksdksldk")
        }
    }

    private fun gotoPostDetails(postId: String) {
        findNavController().navigate(
            PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(postId)
        )
    }
}
package io.gorillas.assignment.presentation.ui.main.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import io.gorillas.assignment.R
import io.gorillas.assignment.databinding.FargmentPostsBinding
import io.gorillas.assignment.di.Injectable
import io.gorillas.assignment.presentation.adapters.PostsListAdapter
import io.gorillas.assignment.presentation.adapters.PostsListLoadStateAdapter
import io.gorillas.assignment.presentation.common.asMergedLoadStates
import io.gorillas.assignment.presentation.common.binding.FragmentDataBindingComponent
import io.gorillas.assignment.presentation.ui.main.MainActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class PostsFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var viewModel: PostsViewModel

    private lateinit var binding: FargmentPostsBinding

    private lateinit var adapter: PostsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fargment_posts,
            container,
            false,
            dataBindingComponent
        )
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[PostsViewModel::class.java]

        initAdapter()

        initSwipeToRefresh()

        viewModel.getPosts("")
    }

    private fun initAdapter() {
        adapter = PostsListAdapter { post ->
            gotoPostDetails(post.id)
        }
        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PostsListLoadStateAdapter(adapter),
            footer = PostsListLoadStateAdapter(adapter)
        )

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.challenges.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .asMergedLoadStates()
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.list.scrollToPosition(0) }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun gotoPostDetails(postId: String) {
        findNavController().navigate(
            PostsFragmentDirections.actionPostsFragmentToPostDetailFragment(postId)
        )
    }
}
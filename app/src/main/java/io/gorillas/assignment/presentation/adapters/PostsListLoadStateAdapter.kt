package io.gorillas.assignment.presentation.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import io.gorillas.assignment.presentation.common.NetworkStateItemViewHolder

class PostsListLoadStateAdapter(
    private val adapter: PostsListAdapter
) : LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent) { adapter.retry() }
    }
}
package io.gorillas.assignment.presentation.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import io.gorillas.assignment.domain.model.PostModel
import io.gorillas.assignment.presentation.viewholders.PostsListViewHolder

class PostsListAdapter(
    private val postClickCallback: ((PostModel) -> Unit)?
) : PagingDataAdapter<PostModel, PostsListViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: PostsListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: PostsListViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.updatePost(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsListViewHolder {
        return PostsListViewHolder.create(parent, postClickCallback)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem.id == newItem.id
        }
    }
}

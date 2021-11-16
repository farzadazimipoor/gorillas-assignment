package io.gorillas.assignment.presentation.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.gorillas.assignment.R
import io.gorillas.assignment.domain.model.PostModel

class PostsListViewHolder(
    view: View,
    private val postClickCallback: ((PostModel) -> Unit)?
) : RecyclerView.ViewHolder(view) {
    private val txtTitle: TextView = view.findViewById(R.id.txtTitle)
    private val txtBody: TextView = view.findViewById(R.id.txtBody)
    private var post: PostModel? = null

    init {
        view.setOnClickListener {
            post?.id?.let {
                postClickCallback?.invoke(post!!)
            }
        }
    }

    fun bind(post: PostModel?) {
        this.post = post
        txtTitle.text = post?.title ?: ""
        txtBody.text = truncatePostBody(post?.body ?: "")
    }

    private fun truncatePostBody(body: String): String {
        var result = body.take(120)
        if (body.length > 120) {
            result += " ..."
        }
        return result
    }

    companion object {
        fun create(parent: ViewGroup, postClickCallback: ((PostModel) -> Unit)?): PostsListViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item_row, parent, false)
            return PostsListViewHolder(view, postClickCallback)
        }
    }

    fun updatePost(item: PostModel?) {
        post = item
    }
}
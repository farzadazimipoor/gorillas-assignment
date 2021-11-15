package io.gorillas.assignment.domain.repository

import androidx.paging.PagingData
import io.gorillas.assignment.domain.model.PostDetailModel
import io.gorillas.assignment.domain.model.PostModel
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getAllPosts(query: String, limit: Int): Flow<PagingData<PostModel>>
    suspend fun getPost(id: String): PostDetailModel
}
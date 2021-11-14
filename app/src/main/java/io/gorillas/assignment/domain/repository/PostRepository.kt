package io.gorillas.assignment.domain.repository

import io.gorillas.assignment.GetAllPostsQuery
import io.gorillas.assignment.GetPostQuery
import io.gorillas.assignment.type.PageQueryOptions

interface PostRepository {
    suspend fun getAllPosts(options: PageQueryOptions): GetAllPostsQuery.Data?
    suspend fun getPost(id: String): GetPostQuery.Data?
}
package io.gorillas.assignment.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import io.gorillas.assignment.GetAllPostsQuery
import io.gorillas.assignment.GetPostQuery
import io.gorillas.assignment.domain.repository.PostRepository
import io.gorillas.assignment.type.PageQueryOptions
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : PostRepository {
    override suspend fun getAllPosts(options: PageQueryOptions): GetAllPostsQuery.Data? {
        return apolloClient.query(GetAllPostsQuery(Input.optional(options))).await().data
    }

    override suspend fun getPost(id: String): GetPostQuery.Data? {
        return apolloClient.query(GetPostQuery(id)).await().data
    }
}
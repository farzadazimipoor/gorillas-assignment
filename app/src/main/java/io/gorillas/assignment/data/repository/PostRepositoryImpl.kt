package io.gorillas.assignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import io.gorillas.assignment.GetPostQuery
import io.gorillas.assignment.data.remote.PostsPagingSource
import io.gorillas.assignment.domain.model.PostDetailModel
import io.gorillas.assignment.domain.model.PostModel
import io.gorillas.assignment.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : PostRepository {
    override fun getAllPosts(query: String, limit: Int): Flow<PagingData<PostModel>> {
        val flow = Pager(PagingConfig(pageSize = limit)) {
            PostsPagingSource(apolloClient = apolloClient, query = query)
        }.flow
        return flow
    }

    override suspend fun getPost(id: String): PostDetailModel {
        val response = apolloClient.query(GetPostQuery(id)).await().data
        return PostDetailModel(
            id = response?.post?.id ?: "",
            title = response?.post?.title ?: "",
            body = response?.post?.body ?: "",
        )
    }
}
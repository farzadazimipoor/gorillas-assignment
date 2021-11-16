package io.gorillas.assignment.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import io.gorillas.assignment.GetAllPostsQuery
import io.gorillas.assignment.domain.model.PostModel
import io.gorillas.assignment.type.PageQueryOptions
import io.gorillas.assignment.type.PaginateOptions
import io.gorillas.assignment.type.SearchOptions
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

class PostsPagingSource(
    private val apolloClient: ApolloClient,
    private val query: String
) : PagingSource<Int, PostModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostModel> {
        try {
            val nextPageNumber = params.key ?: 1

            val queryOptions = PageQueryOptions(
                paginate = Input.optional(PaginateOptions(page = Input.optional(nextPageNumber), limit = Input.optional(params.loadSize))),
                search = Input.optional(SearchOptions(Input.optional(query)))
            )

            val response = apolloClient.query(GetAllPostsQuery(Input.optional(queryOptions))).await().data

            var posts = listOf<PostModel>()

            if (response?.posts?.data != null) {
                posts = response.posts.data.map { x -> PostModel(x?.id ?: "", x?.title ?: "", x?.body ?: "") }
            }

            return LoadResult.Page(
                data = posts,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: SocketException) {
            return LoadResult.Error(e)
        } catch (e: ConnectException) {
            return LoadResult.Error(e)
        } catch (e: UnknownHostException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
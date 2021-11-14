package io.gorillas.assignment.data.remote

import com.apollographql.apollo.ApolloClient
import io.gorillas.assignment.common.Constants
import okhttp3.OkHttpClient

class ApolloGraphQLClient {
    companion object {
        private fun setupApolloClient(): ApolloClient.Builder {
            val okHttpClient = OkHttpClient.Builder()
            return ApolloClient.builder()
                .serverUrl(Constants.SERVER_URL)
                .okHttpClient(okHttpClient.build())
        }

        fun create(): ApolloClient {
            return setupApolloClient().build()
        }
    }
}
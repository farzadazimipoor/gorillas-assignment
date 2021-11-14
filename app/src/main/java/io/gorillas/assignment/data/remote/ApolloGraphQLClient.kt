package io.gorillas.assignment.data.remote

import com.apollographql.apollo.ApolloClient
import io.gorillas.assignment.common.Constants
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ApolloGraphQLClient {
    companion object {
        private fun setupApolloClient(): ApolloClient.Builder {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(HttpInterceptor())
            httpClient.readTimeout(120, TimeUnit.SECONDS)
            httpClient.connectTimeout(120, TimeUnit.SECONDS)
            return ApolloClient.builder()
                .serverUrl(Constants.SERVER_URL)
                .okHttpClient(httpClient.build())
        }

        fun create(): ApolloClient {
            return setupApolloClient().build()
        }
    }
}
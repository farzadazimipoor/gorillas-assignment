package io.gorillas.assignment.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import io.gorillas.assignment.data.remote.ApolloGraphQLClient
import io.gorillas.assignment.data.repository.PostRepositoryImpl
import io.gorillas.assignment.domain.repository.PostRepository
import javax.inject.Singleton

@Module(includes = [ViewModelBuilderModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloGraphQLClient.create()
    }

    @Singleton
    @Provides
    fun providePostRepository(apolloClient: ApolloClient): PostRepository {
        return PostRepositoryImpl(apolloClient)
    }
}
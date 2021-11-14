package io.gorillas.assignment.di

import android.app.Application
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import io.gorillas.assignment.data.remote.ApolloGraphQLClient
import javax.inject.Singleton

@Module(includes = [ViewModelBuilderModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloGraphQLClient.create()
    }
}
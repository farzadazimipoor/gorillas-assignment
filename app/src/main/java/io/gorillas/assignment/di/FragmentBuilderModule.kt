package io.gorillas.assignment.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.gorillas.assignment.presentation.ui.main.postdetail.PostDetailFragment
import io.gorillas.assignment.presentation.ui.main.posts.PostsFragment

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributePostsFragment(): PostsFragment

    @ContributesAndroidInjector
    abstract fun contributePostDetailsFragment(): PostDetailFragment
}
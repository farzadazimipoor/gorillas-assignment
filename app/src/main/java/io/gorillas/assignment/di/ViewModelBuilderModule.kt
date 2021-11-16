package io.gorillas.assignment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.gorillas.assignment.common.annotation.ViewModelKey
import io.gorillas.assignment.common.helpers.ViewModelFactory
import io.gorillas.assignment.presentation.ui.main.postdetail.PostDetailViewModel
import io.gorillas.assignment.presentation.ui.main.posts.PostsViewModel

@Module
abstract class ViewModelBuilderModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(postsViewModel: PostsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    abstract fun bindPostDetailViewModel(postDetailViewModel: PostDetailViewModel): ViewModel
}
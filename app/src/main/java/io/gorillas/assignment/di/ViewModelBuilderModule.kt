package io.gorillas.assignment.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import io.gorillas.assignment.common.helpers.ViewModelFactory

@Module
abstract class ViewModelBuilderModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
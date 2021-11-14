package io.gorillas.assignment.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.gorillas.assignment.presentation.ui.main.MainActivity

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
package io.gorillas.assignment.presentation

import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.gorillas.assignment.di.Injectable
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasAndroidInjector, Injectable {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
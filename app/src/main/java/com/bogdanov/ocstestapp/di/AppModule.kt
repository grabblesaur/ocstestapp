package com.bogdanov.ocstestapp.di

import com.bogdanov.ocstestapp.di.activity.ActivityScope
import com.bogdanov.ocstestapp.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector
    fun mainActivity(): MainActivity
}

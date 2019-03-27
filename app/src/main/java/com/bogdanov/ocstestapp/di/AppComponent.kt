package com.bogdanov.ocstestapp.di

import android.content.Context
import com.bogdanov.ocstestapp.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class),
    (ApiModule::class)
])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }


    fun inject(application: Application)

}

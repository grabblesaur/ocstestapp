package com.bogdanov.ocstestapp.di

import com.bogdanov.ocstestapp.BuildConfig
import com.bogdanov.ocstestapp.data.api.Api
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ApiModule {

    companion object {
        private const val CONNECT_TIMEOUT = 20L
        private const val READ_TIMEOUT = 20L
        private const val WRITE_TIMEOUT = 20L

        const val DEFAULT_API_HOST = "https://jobs.github.com/"
    }

    @Provides
    internal fun provideNetworkInterceptors(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor)
            : OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
        return okHttpClientBuilder
            .build()
    }

    @Singleton
    @Provides
    internal fun provideGson(): Gson = GsonBuilder()
        .create()

    @Singleton
    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson)
            : Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .createWithScheduler(Schedulers.io())
            )
            .baseUrl(DEFAULT_API_HOST)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideApi(retrofitBuilder: Retrofit): Api {
        return retrofitBuilder.create(Api::class.java)
    }
}
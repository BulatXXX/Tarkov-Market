package com.singularity.tarkov_market_data.di

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
internal class FleaMarketNetworkModule {
    @Provides
    fun provideHttpLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { msg -> Log.d("HTTP-Tarkov", msg) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttp(
        logging: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout   (15, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideApolloClient(
        okHttpClient: OkHttpClient
    ): ApolloClient =
        ApolloClient.Builder()
            .serverUrl("https://api.tarkov.dev/graphql")
            .okHttpClient(okHttpClient)
            .build()
}
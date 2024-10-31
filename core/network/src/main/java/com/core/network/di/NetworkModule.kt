package com.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Tüm request ve response body'leri gösterir
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(logging)
            addInterceptor { chain ->
                val originalReq = chain.request()
                val requestWithUserAgent: Request = originalReq.newBuilder()
                    .header(
                        "x-rapidapi-key", "59605dc3de167bb65847fc263612c55e"
                    )
                    .header("x-rapidapi-host", "v3.football.api-sports.io")
                    .build()
                val resp = chain.proceed(requestWithUserAgent)
                resp
            }
        }.build()


        return Retrofit
            .Builder()
            .baseUrl("https://v3.football.api-sports.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
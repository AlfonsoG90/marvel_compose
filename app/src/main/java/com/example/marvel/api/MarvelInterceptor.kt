package com.example.marvel.api

import com.example.marvel.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MarvelInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("apikey",BuildConfig.PUBLIC_KEY)
            .addQueryParameter("ts",BuildConfig.TS)
            .addQueryParameter("hash",BuildConfig.HASH)
            .build()

        val requestBuilder = original.newBuilder().url(url)
        return chain.proceed(requestBuilder.build())
    }
}
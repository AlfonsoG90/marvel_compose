package com.example.marvel.api

import com.example.marvel.BuildConfig
import com.example.marvel.utils.md5
import okhttp3.Interceptor
import okhttp3.Response

class MarvelInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val timestamp = System.currentTimeMillis().toString()
        val hash = md5(timestamp + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY)
        val original = chain.request()
        val url = original.url.newBuilder()
            .addQueryParameter("apikey",BuildConfig.PUBLIC_KEY)
            .addQueryParameter("ts",timestamp)
            .addQueryParameter("hash",hash)
            .build()

        val requestBuilder = original.newBuilder().url(url)
        return chain.proceed(requestBuilder.build())
    }
}
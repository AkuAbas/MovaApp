package com.example.mova.api

import com.example.mova.utils.Constants.API_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val apiToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder().addHeader("Authorization", "Bearer $apiToken").build()

        return chain.proceed(newRequest)

    }
}
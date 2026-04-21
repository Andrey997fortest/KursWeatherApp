package ru.istu.kursgoldenringweather.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiNinjasHeaderInterceptor(
    private val apiKey: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        if (apiKey.isNotBlank()) {
            requestBuilder.addHeader("X-Api-Key", apiKey)
        }
        return chain.proceed(requestBuilder.build())
    }
}

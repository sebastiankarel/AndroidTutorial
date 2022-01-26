package de.sebastiankarel.tutorialapplication.model.persistence

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val appPreferences: AppPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val builder = request.newBuilder()
        appPreferences.getToken()?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }
        val response = chain.proceed(builder.build())

        if (response.code() == 401) {
            appPreferences.removeToken()
        }

        return response
    }
}
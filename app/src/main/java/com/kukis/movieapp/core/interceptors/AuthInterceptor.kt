package com.kukis.movieapp.core.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .header("Authorization", tokenManager.getToken())
            .header("accept", getContentType())
            .build()
        return chain.proceed(request)
    }

    private fun getContentType() = "application/json"
}

class TokenManager @Inject constructor() {
    fun getToken(): String =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDgxYjFmZWFiNGU1MzAzOGFlMzZmYmM4OThhMTNhYSIsInN1YiI6IjY0NDg0NzU3NmEyMjI3MDRmOGQxMGJhOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vDtGlZYXuJiTcLnii03meq1nnRJ74TZFE5xCNcctc3k"

}
package com.example.marketplace.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface CompilerApiService {

    @Headers("Content-Type: application/json")
    @POST("execute")
    fun executeCode(@Body request: CompileRequest): Call<CompileResponse>
}

data class CompileRequest(
    val script: String,
    val language: String = "kotlin",
    val versionIndex: String = "4",
    val clientId: String,
    val clientSecret: String
)

data class CompileResponse(
    val output: String
)

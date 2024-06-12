package com.example.marketplace.model

data class RegisterRequest(
    val username: String,
    val password: String
)

data class InfoResponse(val info: String)

data class NewsResponse(val news: List<String>)

data class ReviewsResponse(val reviews: List<String>)

data class ReviewRequest(val review: String)

data class ReviewResponse(val message: String)

data class ApiResponse(val message: String)

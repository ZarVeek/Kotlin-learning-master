package com.example.marketplace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import ApiService
import com.example.marketplace.Module.ModuleDetailActivity
import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.dataClasses.ModuleData
import com.example.marketplace.model.InfoResponse
import com.example.marketplace.model.NewsResponse
import com.example.marketplace.model.ReviewRequest
import com.example.marketplace.model.ReviewResponse
import com.example.marketplace.model.ReviewsResponse
import com.example.marketplace.ui.ManualTestingScreen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualTestingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                var selectedAction by remember { mutableStateOf("Инфо") }
                var infoText by remember { mutableStateOf("") }
                var newsText by remember { mutableStateOf("") }
                var reviews by remember { mutableStateOf(listOf<String>()) }
                var newReview by remember { mutableStateOf(TextFieldValue("")) }

                val onBackArrowClicked = {
                    startActivity(Intent(this, MainActivity::class.java))
                }

                val onModuleClicked: (ModuleData) -> Unit = { module ->
                    val intent = Intent(this, ModuleDetailActivity::class.java)
                    intent.putExtra("module", module)
                    startActivity(intent)
                }

                fetchInfo { infoResponse ->
                    infoText = infoResponse.info
                }

                fetchNews { newsResponse ->
                    newsText = newsResponse.news.joinToString("\n")
                }

                fetchReviews { reviewsResponse ->
                    reviews = reviewsResponse.reviews
                }

                val onSendReviewClicked = { review: String ->
                    addReview(review) { reviewResponse ->
                        Toast.makeText(this, reviewResponse.message, Toast.LENGTH_SHORT).show()
                        fetchReviews { reviewsResponse ->
                            reviews = reviewsResponse.reviews
                        }
                    }
                }

                ManualTestingScreen(
                    selectedAction = selectedAction,
                    onBackArrowClicked = onBackArrowClicked,
                    onInfoClicked = { selectedAction = "Инфо" },
                    onModulesClicked = { selectedAction = "Модули" },
                    onNewsClicked = { selectedAction = "Новости" },
                    onReviewsClicked = { selectedAction = "Отзывы" },
                    onModuleClicked = onModuleClicked,
                    infoText = infoText,
                    newsText = newsText,
                    reviews = reviews,
                    newReview = newReview,
                    onNewReviewChanged = { newReview = it },
                    onSendReviewClicked = onSendReviewClicked
                )
            }
        }
    }

    private fun fetchInfo(onResult: (InfoResponse) -> Unit) {
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retrofitService.getInfo()

        call.enqueue(object : Callback<InfoResponse> {
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                Log.e("Info", "Failed to fetch info: ${t.message}")
                t.printStackTrace()
                showErrorToast()
            }
        })
    }

    private fun fetchNews(onResult: (NewsResponse) -> Unit) {
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retrofitService.getNews()

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("News", "Failed to fetch news: ${t.message}")
                t.printStackTrace()
                showErrorToast()
            }
        })
    }

    private fun fetchReviews(onResult: (ReviewsResponse) -> Unit) {
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retrofitService.getReviews()

        call.enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                Log.e("Reviews", "Failed to fetch reviews: ${t.message}")
                t.printStackTrace()
                showErrorToast()
            }
        })
    }

    private fun addReview(review: String, onResult: (ReviewResponse) -> Unit) {
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)
        val call = retrofitService.addReview(ReviewRequest(review))

        call.enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it) }
                } else {
                    showErrorToast()
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Log.e("AddReview", "Failed to add review: ${t.message}")
                t.printStackTrace()
                showErrorToast()
            }
        })
    }

    private fun showErrorToast() {
        Toast.makeText(this@ManualTestingActivity, "Ошибка запроса", Toast.LENGTH_SHORT).show()
    }
}

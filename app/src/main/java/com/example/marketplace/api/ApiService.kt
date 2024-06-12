
import com.example.marketplace.model.AuthRequest
import com.example.marketplace.model.AuthResponse
import com.example.marketplace.model.InfoResponse
import com.example.marketplace.model.NewsResponse
import com.example.marketplace.model.RegisterRequest
import com.example.marketplace.model.RegisterResponse
import com.example.marketplace.model.ReviewRequest
import com.example.marketplace.model.ReviewResponse
import com.example.marketplace.model.ReviewsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @POST("login")
    fun authenticateUser(
        @Body authRequest: AuthRequest
    ): Call<AuthResponse>

    @POST("register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("/info")
    fun getInfo(): Call<InfoResponse>

    @GET("/news")
    fun getNews(): Call<NewsResponse>

    @GET("/reviews")
    fun getReviews(): Call<ReviewsResponse>

    @POST("/reviews")
    fun addReview(@Body review: ReviewRequest): Call<ReviewResponse>
}

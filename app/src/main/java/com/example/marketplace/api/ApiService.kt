
import com.example.marketplace.dataClasses.ModuleData
import com.example.marketplace.model.AuthRequest
import com.example.marketplace.model.AuthResponse
import com.example.marketplace.model.RegisterRequest
import com.example.marketplace.model.RegisterResponse
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

    @GET("/modules")
    suspend fun getModules(): List<ModuleData>
}

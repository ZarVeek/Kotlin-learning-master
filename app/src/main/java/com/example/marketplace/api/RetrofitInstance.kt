package com.example.marketplace.api

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitInstance {

    private const val BASE_URL = "http://192.168.0.108:5000/"
    private const val JDoodle_BASE_URL = "https://api.jdoodle.com/v1/"

    fun getRetrofitInstance(): Retrofit {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        // Инициализация SSLContext для использования нашего TrustManager
        val sslContext = SSLContext.getInstance("SSL").apply {
            init(null, trustAllCerts, java.security.SecureRandom())
        }

        // Логирование HTTP запросов и ответов
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Создание OkHttpClient с нашими настройками
        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .addInterceptor(loggingInterceptor)
            .hostnameVerifier { _, _ -> true }  // Позволяет всем hostname (не рекомендуется для production)
            .build()

        // Создание экземпляра Retrofit для основного сервера
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Создание экземпляра Retrofit для JDoodle API
        val jdoodleRetrofit = Retrofit.Builder()
            .baseUrl(JDoodle_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit
    }

    fun getJDoodleRetrofitInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(JDoodle_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}

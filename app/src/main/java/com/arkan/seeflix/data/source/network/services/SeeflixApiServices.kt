package com.arkan.seeflix.data.source.network.services

import com.arkan.seeflix.BuildConfig
import com.arkan.seeflix.data.source.network.model.MovieDetailResponse
import com.arkan.seeflix.data.source.network.model.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface SeeflixApiServices {
    @GET("popular")
    suspend fun getBannerImgHome(): MoviesResponse

    @GET("now_playing")
    suspend fun getNowPlaying(): MoviesResponse

    @GET("popular")
    suspend fun getPopular(): MoviesResponse

    @GET("upcoming")
    suspend fun getUpcoming(): MoviesResponse

    @GET("top_rated")
    suspend fun getTopRated(): MoviesResponse

    @GET("{id}")
    suspend fun getMovieDetail(
        @Path("id") id: String?,
    ): MovieDetailResponse

    companion object {
        @JvmStatic
        operator fun invoke(): SeeflixApiServices {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor { chain ->
                        val request =
                            chain.request().newBuilder()
                                .addHeader("accept", "application/json")
                                .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
                        chain.proceed(request.build())
                    }
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(SeeflixApiServices::class.java)
        }
    }
}

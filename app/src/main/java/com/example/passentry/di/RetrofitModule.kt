package com.example.passentry.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.passentry.data.LoginRepository
import com.example.passentry.data.LoginRepositoryImp
import com.example.passentry.data.remote.service.PassEntryService
import com.example.passentry.utils.AUTH_TOKEN
import com.example.passentry.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            "APP_INFO", Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        appInfo: SharedPreferences
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequestBuilder = originalRequest.newBuilder()

                // Fetch the token from SharedPreferences
                val token = appInfo.getString(AUTH_TOKEN, null)
                if (token != null) {
                    // Add the Authorization header with the Bearer token
                    newRequestBuilder.addHeader("Authorization", "Bearer $token")
                }

                // Add the Content-Type header
                newRequestBuilder.addHeader("Content-Type", "application/json")

                // Preserve the original request method and body
                newRequestBuilder.method(originalRequest.method, originalRequest.body)

                // Proceed with the new request
                chain.proceed(newRequestBuilder.build())
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .build()

    @Provides
    @Singleton
    internal fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        Log.d("provideRetrofit", BASE_URL)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): PassEntryService {
        return retrofit.create(PassEntryService::class.java)
    }


    @Singleton
    @Provides
    fun provideLoginRepository(
        passEntryService: PassEntryService,
        appInfo: SharedPreferences
    ): LoginRepository {
        return LoginRepositoryImp(passEntryService, appInfo)
    }


    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

}

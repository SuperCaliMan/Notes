package com.supercaliman.notification.di

import com.supercaliman.notification.api.FcmApi
import com.supercaliman.notification.data.FcmRepoImpl
import com.supercaliman.notification.domain.FcmRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun getFcmApi(): FcmApi {

        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()

        logging.level = HttpLoggingInterceptor.Level.HEADERS
        httpClient.addInterceptor(logging)


        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") //TODO aggiungere parametro per il server e creare una flavor di demo che punta a heroku
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(FcmApi::class.java)
    }

    @Provides
    @Singleton
    fun getFcmRepo(api: FcmApi): FcmRepo {
        return FcmRepoImpl(api)
    }
}
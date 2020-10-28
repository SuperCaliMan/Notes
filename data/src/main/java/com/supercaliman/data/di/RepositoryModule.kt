package com.supercaliman.data.di


import com.google.gson.GsonBuilder
import com.supercaliman.data.FireStoreAPI
import com.supercaliman.data.FireStoreRepoImp
import com.supercaliman.data.RemoteConfigImp
import com.supercaliman.domain.RemoteConfigApp
import com.supercaliman.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun getRepository(api: FireStoreAPI): Repository {
        return FireStoreRepoImp(api)
    }


    @Provides
    @Singleton
    fun getRemoteConfig(): RemoteConfigApp {
        val gsn = GsonBuilder().create()
        return RemoteConfigImp(
            gsn
        )
    }
}
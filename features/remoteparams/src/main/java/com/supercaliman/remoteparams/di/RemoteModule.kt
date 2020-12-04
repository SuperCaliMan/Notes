package com.supercaliman.remoteparams.di

import com.squareup.moshi.Moshi
import com.supercaliman.remoteparams.data.RemoteConfigImp
import com.supercaliman.remoteparams.domain.RemoteConfigApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RemoteModule {



    @Provides
    @Singleton
    fun getRemoteConfig(): RemoteConfigApp {
        return RemoteConfigImp(Moshi.Builder().build())
    }
}